package com.fever.event.test.fever_demo_miguelcabezas.application.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fever.event.test.fever_demo_miguelcabezas.application.dto.EventSummary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * EventList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-22T12:56:50.571266200+01:00[Europe/Madrid]")
public class EventList {

  @Valid
  private List<@Valid EventSummary> events = new ArrayList<>();

  public EventList() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public EventList(List<@Valid EventSummary> events) {
    this.events = events;
  }

  public EventList events(List<@Valid EventSummary> events) {
    this.events = events;
    return this;
  }

  public EventList addEventsItem(EventSummary eventsItem) {
    if (this.events == null) {
      this.events = new ArrayList<>();
    }
    this.events.add(eventsItem);
    return this;
  }

  /**
   * Get events
   * @return events
  */
  @NotNull @Valid 
  @Schema(name = "events", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("events")
  public List<@Valid EventSummary> getEvents() {
    return events;
  }

  public void setEvents(List<@Valid EventSummary> events) {
    this.events = events;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventList eventList = (EventList) o;
    return Objects.equals(this.events, eventList.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventList {\n");
    sb.append("    events: ").append(toIndentedString(events)).append("\n");
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

