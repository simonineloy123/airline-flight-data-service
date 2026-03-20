package com.airline.flightdata.domain.event;

import com.airline.flightdata.domain.model.Flight;

public class FlightCreatedEvent extends DomainEvent {

    private static final String EVENT_TYPE = "flight_created";

    private final Flight flight;

    public FlightCreatedEvent(Flight flight) {
        super(EVENT_TYPE, flight.getId().toString());
        this.flight = flight;
    }

    public Flight getFlight() { return flight; }
}
