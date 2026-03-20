package com.airline.flightdata.infrastructure.adapter.in.rest.dto;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlightResponse(
    UUID id,
    String flightNumber,
    String origin,
    String destination,
    String aircraftId,
    LocalDateTime scheduledDep,
    LocalDateTime scheduledArr,
    LocalDateTime actualDep,
    LocalDateTime actualArr,
    FlightStatus status,
    int delayMinutes,
    int passengers,
    String gate,
    boolean isDelayed,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static FlightResponse from(Flight flight) {
        return new FlightResponse(
            flight.getId(),
            flight.getFlightNumber(),
            flight.getOrigin(),
            flight.getDestination(),
            flight.getAircraftId(),
            flight.getScheduledDep(),
            flight.getScheduledArr(),
            flight.getActualDep(),
            flight.getActualArr(),
            flight.getStatus(),
            flight.getDelayMinutes(),
            flight.getPassengers(),
            flight.getGate(),
            flight.isDelayed(),
            flight.getCreatedAt(),
            flight.getUpdatedAt()
        );
    }
}
