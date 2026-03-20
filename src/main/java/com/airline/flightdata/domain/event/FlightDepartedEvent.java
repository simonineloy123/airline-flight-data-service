package com.airline.flightdata.domain.event;

import com.airline.flightdata.domain.model.Flight;
import java.time.LocalDateTime;

public class FlightDepartedEvent extends DomainEvent {

    private static final String EVENT_TYPE = "flight_departed";

    private final Flight flight;
    private final LocalDateTime actualDepartureTime;

    public FlightDepartedEvent(Flight flight, LocalDateTime actualDepartureTime) {
        super(EVENT_TYPE, flight.getId().toString());
        this.flight               = flight;
        this.actualDepartureTime  = actualDepartureTime;
    }

    public Flight getFlight()                    { return flight; }
    public LocalDateTime getActualDepartureTime() { return actualDepartureTime; }
}
