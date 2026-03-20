package com.airline.flightdata.application.usecase;

import com.airline.flightdata.domain.event.FlightCreatedEvent;
import com.airline.flightdata.domain.exception.AirportNotFoundException;
import com.airline.flightdata.domain.exception.DuplicateFlightException;
import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.port.in.CreateFlightCommand;
import com.airline.flightdata.domain.port.in.CreateFlightUseCase;
import com.airline.flightdata.domain.port.out.AirportRepositoryPort;
import com.airline.flightdata.domain.port.out.FlightEventPublisherPort;
import com.airline.flightdata.domain.port.out.FlightRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CreateFlightUseCaseImpl implements CreateFlightUseCase {

    private static final Logger LOG = Logger.getLogger(CreateFlightUseCaseImpl.class);

    private final FlightRepositoryPort     flightRepository;
    private final AirportRepositoryPort    airportRepository;
    private final FlightEventPublisherPort eventPublisher;
    private final Event<FlightCreatedEvent> flightCreatedEvent;

    public CreateFlightUseCaseImpl(
        FlightRepositoryPort flightRepository,
        AirportRepositoryPort airportRepository,
        FlightEventPublisherPort eventPublisher,
        Event<FlightCreatedEvent> flightCreatedEvent
    ) {
        this.flightRepository   = flightRepository;
        this.airportRepository  = airportRepository;
        this.eventPublisher     = eventPublisher;
        this.flightCreatedEvent = flightCreatedEvent;
    }

    @Override
    @Transactional
    public Flight execute(CreateFlightCommand command) {
        LOG.debugf("Creando vuelo: %s", command.flightNumber());

        validateAirportsExist(command.origin(), command.destination());
        validateFlightNumberUnique(command.flightNumber());

        Flight flight = Flight.builder()
            .flightNumber(command.flightNumber())
            .origin(command.origin())
            .destination(command.destination())
            .aircraftId(command.aircraftId())
            .scheduledDep(command.scheduledDep())
            .scheduledArr(command.scheduledArr())
            .passengers(command.passengers())
            .gate(command.gate())
            .build();

        Flight savedFlight = flightRepository.save(flight);

        flightCreatedEvent.fire(new FlightCreatedEvent(savedFlight));

        LOG.infof("Vuelo creado exitosamente: %s [%s]",
            savedFlight.getFlightNumber(), savedFlight.getId());

        return savedFlight;
    }

    public void onFlightCreated(
        @Observes(during = TransactionPhase.AFTER_SUCCESS) FlightCreatedEvent event
    ) {
        eventPublisher.publish(event);
    }

    private void validateAirportsExist(String origin, String destination) {
        if (!airportRepository.existsById(origin)) {
            throw new AirportNotFoundException(origin);
        }
        if (!airportRepository.existsById(destination)) {
            throw new AirportNotFoundException(destination);
        }
    }

    private void validateFlightNumberUnique(String flightNumber) {
        if (flightRepository.existsByFlightNumber(flightNumber)) {
            throw new DuplicateFlightException(flightNumber);
        }
    }
}
