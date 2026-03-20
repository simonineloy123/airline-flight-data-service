package com.airline.flightdata.domain.event;

import com.airline.flightdata.domain.model.Flight;

public class FlightDelayedEvent extends DomainEvent {

    private static final String EVENT_TYPE = "flight_delayed";

    private final Flight flight;
    private final int delayMinutes;

    public FlightDelayedEvent(Flight flight, int delayMinutes) {
        super(EVENT_TYPE, flight.getId().toString());
        this.flight       = flight;
        this.delayMinutes = delayMinutes;
    }

    public Flight getFlight()     { return flight; }
    public int getDelayMinutes()  { return delayMinutes; }
}
