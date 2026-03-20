package com.airline.flightdata.infrastructure.adapter.out.persistence;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import com.airline.flightdata.domain.port.out.FlightRepositoryPort;
import com.airline.flightdata.shared.mapper.FlightMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class FlightRepositoryAdapter implements FlightRepositoryPort {

    private final FlightMapper flightMapper;

    public FlightRepositoryAdapter(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }

    @Override
    public Flight save(Flight flight) {
        FlightEntity entity = flightMapper.toEntity(flight);
        if (entity.id != null && FlightEntity.findByIdOptional(entity.id).isPresent()) {
            entity = FlightEntity.getEntityManager().merge(entity);
        } else {
        FlightEntity.persist(entity);
        }
        return flightMapper.toDomain(entity);
    }

    @Override
    public Optional<Flight> findById(UUID id) {
        return FlightEntity.<FlightEntity>findByIdOptional(id)
            .map(flightMapper::toDomain);
    }

    @Override
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return FlightEntity.findByFlightNumber(flightNumber)
            .map(flightMapper::toDomain);
    }

    @Override
    public List<Flight> findAll() {
        return FlightEntity.<FlightEntity>listAll()
            .stream()
            .map(flightMapper::toDomain)
            .toList();
    }

    @Override
    public List<Flight> findByStatus(FlightStatus status) {
        return FlightEntity.findByStatus(status)
            .stream()
            .map(flightMapper::toDomain)
            .toList();
    }

    @Override
    public List<Flight> findByOrigin(String iataCode) {
        return FlightEntity.findByOrigin(iataCode)
            .stream()
            .map(flightMapper::toDomain)
            .toList();
    }

    @Override
    public List<Flight> findByDestination(String iataCode) {
        return FlightEntity.findByDestination(iataCode)
            .stream()
            .map(flightMapper::toDomain)
            .toList();
    }

    @Override
    public boolean existsByFlightNumber(String flightNumber) {
        return FlightEntity.existsByFlightNumber(flightNumber);
    }

    @Override
    public void deleteById(UUID id) {
        FlightEntity.deleteById(id);
    }
}
