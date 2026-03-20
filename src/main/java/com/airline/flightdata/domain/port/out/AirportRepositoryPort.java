package com.airline.flightdata.domain.port.out;

import com.airline.flightdata.domain.model.Airport;
import java.util.List;
import java.util.Optional;

public interface AirportRepositoryPort {
    Optional<Airport> findById(String iataCode);
    List<Airport> findAll();
    boolean existsById(String iataCode);
}
