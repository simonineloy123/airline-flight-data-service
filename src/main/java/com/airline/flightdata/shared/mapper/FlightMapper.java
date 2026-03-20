package com.airline.flightdata.shared.mapper;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.infrastructure.adapter.out.persistence.FlightEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlightMapper {

    public Flight toDomain(FlightEntity entity) {
        return Flight.builder()
            .id(entity.id)
            .flightNumber(entity.flightNumber)
            .origin(entity.origin)
            .destination(entity.destination)
            .aircraftId(entity.aircraftId)
            .scheduledDep(entity.scheduledDep)
            .scheduledArr(entity.scheduledArr)
            .actualDep(entity.actualDep)
            .actualArr(entity.actualArr)
            .status(entity.status)
            .delayMinutes(entity.delayMinutes)
            .passengers(entity.passengers)
            .gate(entity.gate)
            .createdAt(entity.createdAt)
            .updatedAt(entity.updatedAt)
            .build();
    }

    public FlightEntity toEntity(Flight domain) {
        FlightEntity entity;

        if (domain.getId() != null) {
            FlightEntity existing = FlightEntity.findById(domain.getId());
            entity = existing != null ? existing : new FlightEntity();
        } else {
            entity = new FlightEntity();
        }

        entity.flightNumber  = domain.getFlightNumber();
        entity.origin        = domain.getOrigin();
        entity.destination   = domain.getDestination();
        entity.aircraftId    = domain.getAircraftId();
        entity.scheduledDep  = domain.getScheduledDep();
        entity.scheduledArr  = domain.getScheduledArr();
        entity.actualDep     = domain.getActualDep();
        entity.actualArr     = domain.getActualArr();
        entity.status        = domain.getStatus();
        entity.delayMinutes  = domain.getDelayMinutes();
        entity.passengers    = domain.getPassengers();
        entity.gate          = domain.getGate();
        entity.createdAt     = domain.getCreatedAt();
        entity.updatedAt     = domain.getUpdatedAt();
        return entity;
    }
}
