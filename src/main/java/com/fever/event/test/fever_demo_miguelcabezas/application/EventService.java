package com.fever.event.test.fever_demo_miguelcabezas.application;

import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventList;
import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventListDTO;
import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventSummaryDTO;
import com.fever.event.test.fever_demo_miguelcabezas.domain.Event;
import com.fever.event.test.fever_demo_miguelcabezas.infraestructure.db.MongoDbRepository;
import com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.out.ExternalEventProvider;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final MongoDbRepository mongoDbRepository;
    private final ExternalEventProvider externalEventProvider;
    private static final String SELL_MODE = "online";

    private static final String EXTERNAL_SERVER_UNAVAILABLE = "External server unavailable, retrieving data only from db";
    private static final String EXTERNAL_SERVER_AVAILABLE = "External server available, retrieving data from db and external server";
    private static final String ERROR_GETTING_EVENTS = "Error getting events";
    private static final String FILTERING_EVENTS = "Events dulicated: ";
    private static final String NO_DATA_STORED = "No data stored";

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(MongoDbRepository mongoDbRepository, ExternalEventProvider externalEventProvider) {
        this.mongoDbRepository = mongoDbRepository;
        this.externalEventProvider = externalEventProvider;
    }

    public Mono<EventList> getEvents(LocalDate startsAt, LocalDate endsAt) {
        return externalEventProvider.fetchEvents()
                .flatMap(fetchedEvents -> {
                    if (fetchedEvents.isEmpty()) {
                        logger.debug(EXTERNAL_SERVER_UNAVAILABLE);
                        return getEventsFromDatabases(startsAt, endsAt, SELL_MODE)
                                .collectList()
                                .flatMap(events -> Mono.just(createEventList(events)));
                    } else {
                        logger.debug(EXTERNAL_SERVER_AVAILABLE);
                        return getEventsFromDatabases(startsAt, endsAt, SELL_MODE)
                                .collectList()
                                .defaultIfEmpty(Collections.emptyList())
                                .flatMap(existingEvents -> Mono.just(combineAndFilterEvents(existingEvents,fetchedEvents,startsAt,endsAt)));
                    }
                })
                .onErrorResume(e -> {
                    logger.error(ERROR_GETTING_EVENTS, e);
                    return Mono.just(new EventListDTO());
                });
    }

    private EventList combineAndFilterEvents(List<Event> existngEvents, List<Event> fetchedEvents, LocalDate startsAt, LocalDate endsAt) {
        List<Event> combinedEvents = new ArrayList<>(fetchedEvents);
        combinedEvents.addAll(existngEvents);

        Set<Event> uniqueEventsSet = new HashSet<>(combinedEvents);

        List<Event> eventsInTime = uniqueEventsSet.stream()
                .filter(event -> isEventWithinDateRange(event, startsAt, endsAt))
                .collect(Collectors.toList());

        eventsInTime.forEach(event -> updateMongo(event).subscribe());
        int duplicatedEvents = combinedEvents.size() - eventsInTime.size();
        logger.debug(FILTERING_EVENTS+duplicatedEvents);
        return createEventList(eventsInTime);
    }

    private Flux<Event> getEventsFromDatabases(LocalDate startsAt, LocalDate endsAt, String sellMode) {
        return mongoDbRepository.findByStartsAtBetweenAndSellMode(startsAt, endsAt, sellMode)
                .switchIfEmpty(Flux.defer(() -> {
                    logger.warn(NO_DATA_STORED);
                    return Flux.empty();
                }));
    }

    private Mono<Event> updateMongo(Event event) {
        return mongoDbRepository.save(event)
                .onErrorResume(error -> {
                    logger.error("["+event.getId()+"]"+"Error saving event to MongoDB: " + error.getMessage());
                    return Mono.just(event);
                });
    }

    public boolean isEventWithinDateRange(Event event, LocalDate startsAt, LocalDate endsAt) {

        LocalDate eventStart = event.getStartsAt();
        LocalDate eventEnd = event.getEndsAt();

        return eventStart != null && eventStart.isAfter(startsAt) && eventEnd != null && eventEnd.isBefore(endsAt);
    }

    private EventList createEventList(List<Event> events) {
        EventListDTO eventListDTO = new EventListDTO();
        events.forEach(event -> {
            EventSummaryDTO eventSummary = new EventSummaryDTO();
            eventSummary.setId(event.getId());
            eventSummary.setTitle(event.getTitle());
            eventSummary.setStartDate(event.getStartsAt());
            eventSummary.setEndDate(JsonNullable.of(event.getEndsAt()));
            eventSummary.setMinPrice(JsonNullable.of(event.getMinPrice()));
            eventSummary.setMaxPrice(JsonNullable.of(event.getMaxPrice()));
            eventSummary.setStartTime(JsonNullable.of(event.getStartTime().toString()));
            eventSummary.setEndTime(JsonNullable.of(event.getEndTime().toString()));
            eventListDTO.addEventsItem(eventSummary);
        });
        return eventListDTO;
    }
}
