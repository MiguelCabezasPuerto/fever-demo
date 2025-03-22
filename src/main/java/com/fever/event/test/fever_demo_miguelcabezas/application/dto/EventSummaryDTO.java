package com.fever.event.test.fever_demo_miguelcabezas.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventSummaryDTO extends EventSummary {
    private JsonNullable<LocalDate> endDate = JsonNullable.undefined();
    private JsonNullable<BigDecimal> minPrice = JsonNullable.undefined();
    private JsonNullable<BigDecimal> maxPrice = JsonNullable.undefined();
    private JsonNullable<String> startTime = JsonNullable.undefined();
    private JsonNullable<String> endTime = JsonNullable.undefined();


    @Override
    @JsonInclude(Include.NON_NULL)
    public JsonNullable<LocalDate> getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(JsonNullable<LocalDate> endDate) {
        this.endDate = endDate;
    }

    @Override
    @JsonInclude(Include.NON_NULL)
    public JsonNullable<BigDecimal> getMinPrice() {
        return minPrice;
    }

    @Override
    public void setMinPrice(JsonNullable<BigDecimal> minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    @JsonInclude(Include.NON_NULL)
    public JsonNullable<BigDecimal> getMaxPrice() {
        return maxPrice;
    }

    @Override
    public void setMaxPrice(JsonNullable<BigDecimal> maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    @JsonInclude(Include.NON_NULL)
    public JsonNullable<String> getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(JsonNullable<String> startTime) {
        this.startTime = startTime;
    }

    @Override
    @JsonInclude(Include.NON_NULL)
    public JsonNullable<String> getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(JsonNullable<String> endTime) {
        this.endTime = endTime;
    }
}
