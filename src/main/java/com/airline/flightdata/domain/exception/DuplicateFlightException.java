package com.airline.flightdata.domain.exception;

public class DuplicateFlightException extends RuntimeException {

    public DuplicateFlightException(String flightNumber) {
        super("Ya existe un vuelo con el número: " + flightNumber);
    }
}
