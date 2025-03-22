package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.adapters.out;

import com.fever.event.test.fever_demo_miguelcabezas.domain.*;
import com.fever.event.test.fever_demo_miguelcabezas.infraestructure.exceptions.ProcessResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class ExternalEventProvider {
    private static final String PROVIDER_URL = "https://provider.code-challenge.feverup.com/api";
    private final WebClient webClient;

    private static final String SEND_REQUEST = "Sending request to external provider";

    private static final String ERROR_SERVER_RESPONSE = "Error in server response: {} - {}";

    private static final String TIMEOUT_SERVER_RESPONSE = "Timeout in server response";

    private static final String UNEXPECTED_ERROR = "Unexpected error in server response";

    private static final String ERROR_PARSING_RESPONSE = "Error parsing response";

    private static final String INVALID_DATE = "Invalid date for event";

    private static final Logger logger = LoggerFactory.getLogger(ExternalEventProvider.class);

    public ExternalEventProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(PROVIDER_URL).build();
    }


    @CircuitBreaker(name = "externalEventProvider", fallbackMethod = "fallbackFetchEvents")
    public Mono<List<Event>> fetchEvents() {
        logger.debug(SEND_REQUEST);
        return webClient.get()
                .uri("/events")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::parseEvents)
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException ex) {
                        logger.error(ERROR_SERVER_RESPONSE, ex.getStatusCode(), ex.getResponseBodyAsString(), e);
                    } else if (e instanceof TimeoutException) {
                        logger.error(TIMEOUT_SERVER_RESPONSE, e);
                    } else if (e instanceof ProcessResponseException){
                        logger.error(ERROR_PARSING_RESPONSE,e);
                    }
                    else {
                        logger.error(UNEXPECTED_ERROR, e);
                    }
                    return Mono.just(Collections.emptyList());
                });
    }

    private Mono<List<Event>> parseEvents(String xml) {
        return Mono.fromCallable(() -> {
            try{
                JAXBContext jaxbContext = JAXBContext.newInstance(PlanList.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                PlanList planList = (PlanList) unmarshaller.unmarshal(new StringReader(xml));
                List<Event> events = new ArrayList<>();

                for (BasePlan basePlan : planList.getOutput().getBasePlans()) {
                    Event event = new Event();
                    event.setTitle(basePlan.getTitle());
                    event.setSellMode(basePlan.getSellMode());
                    event.setId(basePlan.getBasePlanId());
                    for (Plan plan : basePlan.getPlans()) {
                        if (parseDateTime(plan.getPlanStartDate()) != null) {
                            event.setStartsAt(parseDateTime(plan.getPlanStartDate()).toLocalDate());
                        }
                        if (parseDateTime(plan.getPlanEndDate()) != null) {
                            event.setEndsAt(parseDateTime(plan.getPlanEndDate()).toLocalDate());
                        }

                        event.setStartTime(parseDateTime(plan.getSellFrom()));
                        event.setEndTime(parseDateTime(plan.getSellTo()));

                        event.setMinPrice(calculateMinPrice(plan.getZones()));
                        event.setMaxPrice(calculateMaxPrice(plan.getZones()));
                    }
                    events.add(event);
                }
                return events;
            }catch(Exception e){
                throw new ProcessResponseException(ERROR_PARSING_RESPONSE);
            }
        });
    }

    public Mono<List<Event>> fallbackFetchEvents(Throwable t) {
        if (t instanceof WebClientResponseException ex) {
            logger.error(ERROR_SERVER_RESPONSE, ex.getStatusCode(), ex.getResponseBodyAsString(), t);
        } else if (t instanceof TimeoutException) {
            logger.error(TIMEOUT_SERVER_RESPONSE, t);
        } else if (t instanceof ProcessResponseException){
        logger.error(ERROR_PARSING_RESPONSE,t);
        }
        else {
            logger.error(UNEXPECTED_ERROR, t);
        }
        return Mono.just(Collections.emptyList());
    }

    public LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            logger.debug(INVALID_DATE,e);
            return null;
        }
    }

    public LocalDateTime parseDateTime(String date) {
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            logger.debug(INVALID_DATE,e);
            return null;
        }
    }

    public BigDecimal calculateMinPrice(List<Zone> zones) {
        Double min =  zones.stream()
                .map(Zone::getPrice)
                .min(Comparable::compareTo)
                .orElse(null);
        return min != null ? BigDecimal.valueOf(min) : null;
    }
    public BigDecimal calculateMaxPrice(List<Zone> zones) {
        Double max =  zones.stream()
                .map(Zone::getPrice)
                .max(Comparable::compareTo)
                .orElse(null);
        return max != null ? BigDecimal.valueOf(max) : null;
    }
}
