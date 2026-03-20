package com.airline.flightdata.application.usecase;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import com.airline.flightdata.domain.port.in.GetFlightUseCase;
import com.airline.flightdata.domain.port.out.FlightRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GetFlightUseCaseImpl implements GetFlightUseCase {

    private final FlightRepositoryPort flightRepository;

    public GetFlightUseCaseImpl(FlightRepositoryPort flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Optional<Flight> findById(UUID id) {
        return flightRepository.findById(id);
    }

    @Override
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> findByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }

    @Override
    public List<Flight> findByOrigin(String iataCode) {
        return flightRepository.findByOrigin(iataCode);
    }

    @Override
    public List<Flight> findByDestination(String iataCode) {
        return flightRepository.findByDestination(iataCode);
    }
}
