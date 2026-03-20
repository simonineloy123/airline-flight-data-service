package com.airline.flightdata.infrastructure.adapter.in.rest.dto;

import com.airline.flightdata.domain.model.Airport;

public record AirportResponse(
    String id,
    String name,
    String city,
    String country,
    String timezone,
    Double latitude,
    Double longitude
) {
    public static AirportResponse from(Airport airport) {
        return new AirportResponse(
            airport.getId(),
            airport.getName(),
            airport.getCity(),
            airport.getCountry(),
            airport.getTimezone(),
            airport.getLatitude(),
            airport.getLongitude()
        );
    }
}
