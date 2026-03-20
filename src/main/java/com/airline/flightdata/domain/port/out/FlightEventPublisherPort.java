package com.airline.flightdata.domain.port.out;

import com.airline.flightdata.domain.event.DomainEvent;

public interface FlightEventPublisherPort {
    void publish(DomainEvent event);
}
