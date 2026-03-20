package com.airline.flightdata.shared.mapper;

import com.airline.flightdata.domain.model.Airport;
import com.airline.flightdata.infrastructure.adapter.out.persistence.AirportEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AirportMapper {

    public Airport toDomain(AirportEntity entity) {
        return Airport.builder()
            .id(entity.id)
            .name(entity.name)
            .city(entity.city)
            .country(entity.country)
            .timezone(entity.timezone)
            .latitude(entity.latitude)
            .longitude(entity.longitude)
            .build();
    }

    public AirportEntity toEntity(Airport domain) {
        AirportEntity entity = new AirportEntity();
        entity.id        = domain.getId();
        entity.name      = domain.getName();
        entity.city      = domain.getCity();
        entity.country   = domain.getCountry();
        entity.timezone  = domain.getTimezone();
        entity.latitude  = domain.getLatitude();
        entity.longitude = domain.getLongitude();
        return entity;
    }
}
