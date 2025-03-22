package com.fever.event.test.fever_demo_miguelcabezas.application;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventList;
import com.fever.event.test.fever_demo_miguelcabezas.domain.Event;
import com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.out.ExternalEventProvider;
import com.fever.event.test.fever_demo_miguelcabezas.infraestructure.db.MongoDbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

class EventServiceTest {

    @Mock
    private MongoDbRepository mongoDbRepository;

    @Mock
    private ExternalEventProvider externalEventProvider;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEvents_NoFetchedEvents() {
        LocalDate startsAt = LocalDate.of(2023, 1, 1);
        LocalDate endsAt = LocalDate.of(2023, 1, 31);
        List<Event> events = Arrays.asList(new Event(), new Event());
        Flux<Event> eventsFlux = Flux.fromIterable(events);

        when(externalEventProvider.fetchEvents()).thenReturn(Mono.just(Collections.emptyList()));
        when(mongoDbRepository.findByStartsAtBetweenAndSellMode(startsAt, endsAt, "online")).thenReturn(eventsFlux);

        Mono<EventList> result = eventService.getEvents(startsAt, endsAt);

        assertNotNull(result.block());
        verify(externalEventProvider).fetchEvents();
        verify(mongoDbRepository).findByStartsAtBetweenAndSellMode(startsAt, endsAt, "online");
    }

    @Test
    void testGetEvents_WithFetchedEvents() {
        LocalDate startsAt = LocalDate.of(2023, 1, 1);
        LocalDate endsAt = LocalDate.of(2023, 1, 31);
        List<Event> fetchedEvents = Arrays.asList(new Event(), new Event());
        List<Event> existingEvents = Arrays.asList(new Event(), new Event());
        Flux<Event> existingEventsFlux = Flux.fromIterable(existingEvents);

        when(externalEventProvider.fetchEvents()).thenReturn(Mono.just(fetchedEvents));
        when(mongoDbRepository.findByStartsAtBetweenAndSellMode(startsAt, endsAt, "online")).thenReturn(existingEventsFlux);
        when(mongoDbRepository.save(any(Event.class))).thenReturn(Mono.just(new Event()));

        Mono<EventList> result = eventService.getEvents(startsAt, endsAt);

        assertNotNull(result.block());
        verify(externalEventProvider).fetchEvents();
        verify(mongoDbRepository).findByStartsAtBetweenAndSellMode(startsAt, endsAt, "online");
    }

    @Test
    void testIsEventWithinDateRange_Valid() {
        LocalDate startsAt = LocalDate.of(2023, 1, 1);
        LocalDate endsAt = LocalDate.of(2023, 1, 31);
        Event event = new Event();
        event.setStartsAt(LocalDate.of(2023, 1, 15));
        event.setEndsAt(LocalDate.of(2023, 1, 20));

        assertTrue(eventService.isEventWithinDateRange(event, startsAt, endsAt));
    }

    @Test
    void testIsEventWithinDateRange_Invalid() {
        LocalDate startsAt = LocalDate.of(2023, 1, 1);
        LocalDate endsAt = LocalDate.of(2023, 1, 31);
        Event event = new Event();
        event.setStartsAt(LocalDate.of(2023, 2, 1));
        event.setEndsAt(LocalDate.of(2023, 2, 5));

        assertFalse(eventService.isEventWithinDateRange(event, startsAt, endsAt));
    }
}