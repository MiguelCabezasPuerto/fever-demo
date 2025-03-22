package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.db;

import com.fever.event.test.fever_demo_miguelcabezas.domain.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MongoDbRepository extends ReactiveMongoRepository<Event, Long> {
    Flux<Event> findByStartsAtBetweenAndSellMode(LocalDate startDate, LocalDate endDate, String sellMode);
}
