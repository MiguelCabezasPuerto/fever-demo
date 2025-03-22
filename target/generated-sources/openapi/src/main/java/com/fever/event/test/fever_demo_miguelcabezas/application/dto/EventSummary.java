package com.fever.event.test.fever_demo_miguelcabezas.application.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * EventSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-22T12:56:50.571266200+01:00[Europe/Madrid]")
public class EventSummary {

  private String id;

  private String title;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  private JsonNullable<String> startTime = JsonNullable.<String>undefined();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private JsonNullable<LocalDate> endDate = JsonNullable.<LocalDate>undefined();

  private JsonNullable<String> endTime = JsonNullable.<String>undefined();

  private JsonNullable<BigDecimal> minPrice = JsonNullable.<BigDecimal>undefined();

  private JsonNullable<BigDecimal> maxPrice = JsonNullable.<BigDecimal>undefined();

  public EventSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public EventSummary(String id, String title, LocalDate startDate, String startTime, LocalDate endDate, String endTime, BigDecimal minPrice, BigDecimal maxPrice) {
    this.id = id;
    this.title = title;
    this.startDate = startDate;
    this.startTime = JsonNullable.of(startTime);
    this.endDate = JsonNullable.of(endDate);
    this.endTime = JsonNullable.of(endTime);
    this.minPrice = JsonNullable.of(minPrice);
    this.maxPrice = JsonNullable.of(maxPrice);
  }

  public EventSummary id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier for the plan
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "Identifier for the plan", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EventSummary title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the plan
   * @return title
  */
  @NotNull 
  @Schema(name = "title", description = "Title of the plan", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public EventSummary startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Date when the event starts in local time
   * @return startDate
  */
  @NotNull @Valid 
  @Schema(name = "start_date", description = "Date when the event starts in local time", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("start_date")
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public EventSummary startTime(String startTime) {
    this.startTime = JsonNullable.of(startTime);
    return this;
  }

  /**
   * Time when the event starts in local time
   * @return startTime
  */
  @NotNull 
  @Schema(name = "start_time", example = "22:38:19", description = "Time when the event starts in local time", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("start_time")
  public JsonNullable<String> getStartTime() {
    return startTime;
  }

  public void setStartTime(JsonNullable<String> startTime) {
    this.startTime = startTime;
  }

  public EventSummary endDate(LocalDate endDate) {
    this.endDate = JsonNullable.of(endDate);
    return this;
  }

  /**
   * Date when the event ends in local time
   * @return endDate
  */
  @NotNull @Valid 
  @Schema(name = "end_date", description = "Date when the event ends in local time", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("end_date")
  public JsonNullable<LocalDate> getEndDate() {
    return endDate;
  }

  public void setEndDate(JsonNullable<LocalDate> endDate) {
    this.endDate = endDate;
  }

  public EventSummary endTime(String endTime) {
    this.endTime = JsonNullable.of(endTime);
    return this;
  }

  /**
   * Time when the event ends in local time
   * @return endTime
  */
  @NotNull 
  @Schema(name = "end_time", example = "14:45:15", description = "Time when the event ends in local time", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("end_time")
  public JsonNullable<String> getEndTime() {
    return endTime;
  }

  public void setEndTime(JsonNullable<String> endTime) {
    this.endTime = endTime;
  }

  public EventSummary minPrice(BigDecimal minPrice) {
    this.minPrice = JsonNullable.of(minPrice);
    return this;
  }

  /**
   * Min price from all the available tickets
   * @return minPrice
  */
  @NotNull @Valid 
  @Schema(name = "min_price", description = "Min price from all the available tickets", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("min_price")
  public JsonNullable<BigDecimal> getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(JsonNullable<BigDecimal> minPrice) {
    this.minPrice = minPrice;
  }

  public EventSummary maxPrice(BigDecimal maxPrice) {
    this.maxPrice = JsonNullable.of(maxPrice);
    return this;
  }

  /**
   * Max price from all the available tickets
   * @return maxPrice
  */
  @NotNull @Valid 
  @Schema(name = "max_price", description = "Max price from all the available tickets", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("max_price")
  public JsonNullable<BigDecimal> getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(JsonNullable<BigDecimal> maxPrice) {
    this.maxPrice = maxPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventSummary eventSummary = (EventSummary) o;
    return Objects.equals(this.id, eventSummary.id) &&
        Objects.equals(this.title, eventSummary.title) &&
        Objects.equals(this.startDate, eventSummary.startDate) &&
        Objects.equals(this.startTime, eventSummary.startTime) &&
        Objects.equals(this.endDate, eventSummary.endDate) &&
        Objects.equals(this.endTime, eventSummary.endTime) &&
        Objects.equals(this.minPrice, eventSummary.minPrice) &&
        Objects.equals(this.maxPrice, eventSummary.maxPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, startDate, startTime, endDate, endTime, minPrice, maxPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventSummary {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    minPrice: ").append(toIndentedString(minPrice)).append("\n");
    sb.append("    maxPrice: ").append(toIndentedString(maxPrice)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

