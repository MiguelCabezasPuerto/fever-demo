package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.in;

import com.fever.event.test.fever_demo_miguelcabezas.application.EventService;
import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RestController
public class EventController implements SearchApi {

    private final EventService eventService;

    private static final String INVALID_INPUT = "Invalid input";
    private static final String RECEIVED_REQUEST = "Received valid request";

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @Override
    public ResponseEntity<EventList> searchEvents(LocalDate startsAt, LocalDate endsAt) {
        if (Boolean.FALSE.equals(isValidInput(startsAt, endsAt))) {
            logger.warn(INVALID_INPUT+ " startsAt: " + startsAt + " endsAt: " + endsAt);
            return ResponseEntity.badRequest().body(null);
        }
        logger.debug(RECEIVED_REQUEST + " startsAt: " + startsAt + " endsAt: " + endsAt);
        return convertMonoEventListDTOOK(eventService.getEvents(startsAt,endsAt));
    }

    private Boolean isValidInput(LocalDate startsAt, LocalDate endsAt) {
        if (startsAt.isAfter(endsAt)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public ResponseEntity<EventList> convertMonoEventListDTOOK(Mono<EventList> monoEventList) {
        return monoEventList.flatMap(eventListDTO -> Mono.just(ResponseEntity.ok(eventListDTO))).block();
    }
}
