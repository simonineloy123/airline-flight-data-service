package com.airline.flightdata.application.usecase;

import com.airline.flightdata.domain.event.*;
import com.airline.flightdata.domain.exception.FlightNotFoundException;
import com.airline.flightdata.domain.exception.InvalidFlightStateException;
import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import com.airline.flightdata.domain.port.in.UpdateFlightStatusUseCase;
import com.airline.flightdata.domain.port.out.FlightEventPublisherPort;
import com.airline.flightdata.domain.port.out.FlightRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class UpdateFlightStatusUseCaseImpl implements UpdateFlightStatusUseCase {

    private static final Logger LOG = Logger.getLogger(UpdateFlightStatusUseCaseImpl.class);

    private final FlightRepositoryPort     flightRepository;
    private final FlightEventPublisherPort eventPublisher;
    private final Event<DomainEvent>       domainEvent;

    public UpdateFlightStatusUseCaseImpl(
        FlightRepositoryPort flightRepository,
        FlightEventPublisherPort eventPublisher,
        Event<DomainEvent> domainEvent
    ) {
        this.flightRepository = flightRepository;
        this.eventPublisher   = eventPublisher;
        this.domainEvent      = domainEvent;
    }

    @Override
    @Transactional
    public Flight execute(UUID flightId, FlightStatus newStatus, int delayMinutes) {
        LOG.debugf("Actualizando estado del vuelo %s → %s", flightId, newStatus);

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new FlightNotFoundException(flightId));

        if (!flight.getStatus().canTransitionTo(newStatus)) {
            throw new InvalidFlightStateException(flight.getStatus(), newStatus);
        }

        applyStatusTransition(flight, newStatus, delayMinutes);

        Flight updatedFlight = flightRepository.save(flight);

        DomainEvent event = buildEvent(updatedFlight, newStatus, delayMinutes);
        if (event != null) {
            domainEvent.fire(event);
        }

        LOG.infof("Estado del vuelo %s actualizado a %s",
            updatedFlight.getFlightNumber(), newStatus);

        return updatedFlight;
    }

    public void onDomainEvent(
        @Observes(during = TransactionPhase.AFTER_SUCCESS) DomainEvent event
    ) {
        eventPublisher.publish(event);
    }

    private void applyStatusTransition(Flight flight, FlightStatus newStatus, int delayMinutes) {
        switch (newStatus) {
            case DELAYED   -> flight.applyDelay(delayMinutes);
            case CANCELLED -> flight.cancel();
            case DEPARTED  -> flight.depart(LocalDateTime.now());
            case LANDED    -> flight.land(LocalDateTime.now());
            default        -> throw new InvalidFlightStateException(flight.getStatus(), newStatus);
        }
    }

    private DomainEvent buildEvent(Flight flight, FlightStatus newStatus, int delayMinutes) {
        return switch (newStatus) {
            case DELAYED   -> new FlightDelayedEvent(flight, delayMinutes);
            case CANCELLED -> new FlightCancelledEvent(flight);
            case DEPARTED  -> new FlightDepartedEvent(flight, flight.getActualDep());
            case LANDED    -> new FlightLandedEvent(flight, flight.getActualArr());
            default        -> null;
        };
    }
}
