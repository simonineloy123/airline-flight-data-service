package com.airline.flightdata.infrastructure.adapter.out.persistence;

import com.airline.flightdata.domain.model.Airport;
import com.airline.flightdata.domain.port.out.AirportRepositoryPort;
import com.airline.flightdata.shared.mapper.AirportMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AirportRepositoryAdapter implements AirportRepositoryPort {

    private final AirportMapper airportMapper;

    public AirportRepositoryAdapter(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }

    @Override
    public Optional<Airport> findById(String iataCode) {
        return AirportEntity.<AirportEntity>findByIdOptional(iataCode)
            .map(airportMapper::toDomain);
    }

    @Override
    public List<Airport> findAll() {
        return AirportEntity.<AirportEntity>listAll()
            .stream()
            .map(airportMapper::toDomain)
            .toList();
    }

    @Override
    public boolean existsById(String iataCode) {
        return AirportEntity.findByIdOptional(iataCode).isPresent();
    }
}
