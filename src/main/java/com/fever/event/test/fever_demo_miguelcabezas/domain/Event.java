package com.fever.event.test.fever_demo_miguelcabezas.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Table
@Data
@Document(collection = "events")
public class Event {
    private String id;
    private String title;
    private LocalDate startsAt;
    @Nullable
    private LocalDateTime startTime;
    @Nullable
    private LocalDate endsAt;
    @Nullable
    private LocalDateTime endTime;
    @Nullable
    private BigDecimal minPrice;
    @Nullable
    private BigDecimal maxPrice;
    private String sellMode;


    public Event() {
    }

    public Event(String id, String title, LocalDate startsAt, LocalDateTime startTime, LocalDate endsAt, LocalDateTime endTime, BigDecimal minPrice, BigDecimal maxPrice, String sellMode) {
        this.id = id;
        this.title = title;
        this.startsAt = startsAt;
        this.startTime = startTime;
        this.endsAt = endsAt;
        this.endTime = endTime;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sellMode = sellMode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartsAt(LocalDate startsAt) {
        this.startsAt = startsAt;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndsAt(LocalDate endsAt) {
        this.endsAt = endsAt;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setSellMode(String sellMode) {
        this.sellMode = sellMode;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndsAt() {
        return endsAt;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public String getSellMode() {
        return sellMode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(startsAt, event.startsAt) &&
                Objects.equals(endsAt, event.endsAt) &&
                Objects.equals(sellMode, event.sellMode) &&
                Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) &&
                Objects.equals(minPrice, event.minPrice) &&
                Objects.equals(maxPrice, event.maxPrice) &&
                Objects.equals(title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startsAt, endsAt, sellMode, startTime, endTime,minPrice,maxPrice,title);
    }
}