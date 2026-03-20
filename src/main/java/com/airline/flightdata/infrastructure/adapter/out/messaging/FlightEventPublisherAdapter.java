package com.airline.flightdata.infrastructure.adapter.out.messaging;

import com.airline.flightdata.domain.event.*;
import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.port.out.FlightEventPublisherPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class FlightEventPublisherAdapter implements FlightEventPublisherPort {

    private static final Logger LOG = Logger.getLogger(FlightEventPublisherAdapter.class);

    private final Emitter<String> flightEventsEmitter;
    private final ObjectMapper    objectMapper;

    public FlightEventPublisherAdapter(
        @Channel("flight-events") Emitter<String> flightEventsEmitter
    ) {
        this.flightEventsEmitter = flightEventsEmitter;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Override
    public void publish(DomainEvent event) {
        try {
            String payload = buildPayload(event);
            flightEventsEmitter.send(payload);
            LOG.infof("Evento publicado → topic: flight_events | type: %s | aggregateId: %s | payload: %s",
                event.getEventType(), event.getAggregateId(), payload);
        } catch (JsonProcessingException e) {
            LOG.errorf("Error serializando evento %s: %s", event.getEventType(), e.getMessage());
            throw new RuntimeException("Error publicando evento a Kafka", e);
        }
    }

    private String buildPayload(DomainEvent event) throws JsonProcessingException {
        ObjectNode node = objectMapper.createObjectNode();

        node.put("eventId",     event.getEventId());
        node.put("eventType",   event.getEventType());
        node.put("aggregateId", event.getAggregateId());
        node.put("occurredAt",  event.getOccurredAt().toString());

        Flight flight = extractFlight(event);
        if (flight != null) {
            node.put("flightNumber",  flight.getFlightNumber());
            node.put("origin",        flight.getOrigin());
            node.put("destination",   flight.getDestination());
            node.put("status",        flight.getStatus().name());
            node.put("delayMinutes",  flight.getDelayMinutes());
            node.put("passengers",    flight.getPassengers());
        }

        if (event instanceof FlightDelayedEvent delayed) {
            node.put("delayMinutes", delayed.getDelayMinutes());
        }

        return objectMapper.writeValueAsString(node);
    }

    private Flight extractFlight(DomainEvent event) {
        return switch (event) {
            case FlightCreatedEvent e  -> e.getFlight();
            case FlightDelayedEvent e  -> e.getFlight();
            case FlightCancelledEvent e -> e.getFlight();
            case FlightDepartedEvent e -> e.getFlight();
            case FlightLandedEvent e   -> e.getFlight();
            default -> null;
        };
    }
}
