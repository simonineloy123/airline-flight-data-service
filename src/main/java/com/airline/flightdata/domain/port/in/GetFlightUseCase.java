package com.airline.flightdata.domain.port.in;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetFlightUseCase {
    Optional<Flight> findById(UUID id);
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findAll();
    List<Flight> findByStatus(FlightStatus status);
    List<Flight> findByOrigin(String iataCode);
    List<Flight> findByDestination(String iataCode);
}
