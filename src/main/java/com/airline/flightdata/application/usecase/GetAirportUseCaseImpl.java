package com.airline.flightdata.application.usecase;

import com.airline.flightdata.domain.model.Airport;
import com.airline.flightdata.domain.port.in.GetAirportUseCase;
import com.airline.flightdata.domain.port.out.AirportRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GetAirportUseCaseImpl implements GetAirportUseCase {

    private final AirportRepositoryPort airportRepository;

    public GetAirportUseCaseImpl(AirportRepositoryPort airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Optional<Airport> findById(String iataCode) {
        return airportRepository.findById(iataCode.toUpperCase());
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }
}
