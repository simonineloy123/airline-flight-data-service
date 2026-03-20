package com.airline.flightdata.domain.event;

import com.airline.flightdata.domain.model.Flight;
import java.time.LocalDateTime;

public class FlightLandedEvent extends DomainEvent {

    private static final String EVENT_TYPE = "flight_landed";

    private final Flight flight;
    private final LocalDateTime actualArrivalTime;

    public FlightLandedEvent(Flight flight, LocalDateTime actualArrivalTime) {
        super(EVENT_TYPE, flight.getId().toString());
        this.flight            = flight;
        this.actualArrivalTime = actualArrivalTime;
    }

    public Flight getFlight()                   { return flight; }
    public LocalDateTime getActualArrivalTime() { return actualArrivalTime; }
}
