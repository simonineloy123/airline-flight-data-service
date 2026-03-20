package com.airline.flightdata.infrastructure.adapter.in.rest.dto;

import com.airline.flightdata.domain.model.FlightStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateFlightStatusRequest(

    @NotNull(message = "El estado es obligatorio")
    FlightStatus status,

    @Min(value = 0, message = "Los minutos de retraso no pueden ser negativos")
    int delayMinutes
) {}
