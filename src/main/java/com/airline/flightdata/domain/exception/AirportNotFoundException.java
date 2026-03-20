package com.airline.flightdata.domain.exception;

public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException(String iataCode) {
        super("Aeropuerto no encontrado con código IATA: " + iataCode);
    }
}
