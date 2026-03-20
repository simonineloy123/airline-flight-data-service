package com.airline.flightdata.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreateFlightRequest(

    @NotBlank(message = "El número de vuelo es obligatorio")
    @Size(min = 2, max = 10, message = "El número de vuelo debe tener entre 2 y 10 caracteres")
    String flightNumber,

    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 3, max = 3, message = "El código IATA debe tener 3 caracteres")
    String origin,

    @NotBlank(message = "El destino es obligatorio")
    @Size(min = 3, max = 3, message = "El código IATA debe tener 3 caracteres")
    String destination,

    String aircraftId,

    @NotNull(message = "La fecha de salida es obligatoria")
    @Future(message = "La fecha de salida debe ser futura")
    LocalDateTime scheduledDep,

    @NotNull(message = "La fecha de llegada es obligatoria")
    @Future(message = "La fecha de llegada debe ser futura")
    LocalDateTime scheduledArr,

    @Min(value = 0, message = "Los pasajeros no pueden ser negativos")
    @Max(value = 853, message = "El máximo de pasajeros es 853")
    int passengers,

    @Size(max = 10, message = "La puerta de embarque no puede superar 10 caracteres")
    String gate
) {}
