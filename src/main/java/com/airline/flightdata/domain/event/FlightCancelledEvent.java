package com.airline.flightdata.domain.event;

import com.airline.flightdata.domain.model.Flight;

public class FlightCancelledEvent extends DomainEvent {

    private static final String EVENT_TYPE = "flight_cancelled";

    private final Flight flight;

    public FlightCancelledEvent(Flight flight) {
        super(EVENT_TYPE, flight.getId().toString());
        this.flight = flight;
    }

    public Flight getFlight() { return flight; }
}
