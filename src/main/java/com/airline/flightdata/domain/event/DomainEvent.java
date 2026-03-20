package com.airline.flightdata.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent {

    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredAt;
    private final String aggregateId;

    protected DomainEvent(String eventType, String aggregateId) {
        this.eventId     = UUID.randomUUID().toString();
        this.eventType   = eventType;
        this.aggregateId = aggregateId;
        this.occurredAt  = LocalDateTime.now();
    }

    public String getEventId()     { return eventId; }
    public String getEventType()   { return eventType; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getAggregateId() { return aggregateId; }
}
