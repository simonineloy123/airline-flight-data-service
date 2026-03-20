package com.airline.flightdata.domain.port.in;

import com.airline.flightdata.domain.model.Airport;
import java.util.List;
import java.util.Optional;

public interface GetAirportUseCase {
    Optional<Airport> findById(String iataCode);
    List<Airport> findAll();
}
