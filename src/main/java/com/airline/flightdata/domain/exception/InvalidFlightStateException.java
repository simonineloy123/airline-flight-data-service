package com.airline.flightdata.domain.exception;

import com.airline.flightdata.domain.model.FlightStatus;

public class InvalidFlightStateException extends RuntimeException {

    public InvalidFlightStateException(FlightStatus current, FlightStatus target) {
        super("Transición de estado inválida: " + current + " → " + target);
    }
}
