package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.in;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fever.event.test.fever_demo_miguelcabezas.application.EventService;
import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchEvents_ValidInput() {
        LocalDate startsAt = LocalDate.of(2023, 1, 1);
        LocalDate endsAt = LocalDate.of(2023, 1, 31);
        EventList eventList = new EventList();
        Mono<EventList> eventListMono = Mono.just(eventList);

        when(eventService.getEvents(startsAt, endsAt)).thenReturn(eventListMono);

        ResponseEntity<EventList> response = eventController.searchEvents(startsAt, endsAt);

        assertEquals(ResponseEntity.ok(eventList), response);
        verify(eventService).getEvents(startsAt, endsAt);
    }

    @Test
    void testSearchEvents_InvalidInput() {
        LocalDate startsAt = LocalDate.of(2023, 1, 31);
        LocalDate endsAt = LocalDate.of(2023, 1, 1);

        ResponseEntity<EventList> response = eventController.searchEvents(startsAt, endsAt);

        assertEquals(ResponseEntity.badRequest().body(null), response);
        verify(eventService, never()).getEvents(any(), any());
    }

    @Test
    void testConvertMonoEventListDTOOK() {
        EventList eventList = new EventList();
        Mono<EventList> eventListMono = Mono.just(eventList);

        ResponseEntity<EventList> response = eventController.convertMonoEventListDTOOK(eventListMono);

        assertEquals(ResponseEntity.ok(eventList), response);
    }
}