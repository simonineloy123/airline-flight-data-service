package com.airline.flightdata.domain.port.out;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepositoryPort {
    Flight save(Flight flight);
    Optional<Flight> findById(UUID id);
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findAll();
    List<Flight> findByStatus(FlightStatus status);
    List<Flight> findByOrigin(String iataCode);
    List<Flight> findByDestination(String iataCode);
    boolean existsByFlightNumber(String flightNumber);
    void deleteById(UUID id);
}
