package com.airline.flightdata.domain.exception;

import java.util.UUID;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(UUID id) {
        super("Vuelo no encontrado con id: " + id);
    }

    public FlightNotFoundException(String flightNumber) {
        super("Vuelo no encontrado con número: " + flightNumber);
    }
}
