package com.airline.flightdata.domain.port.in;

import java.time.LocalDateTime;

public record CreateFlightCommand(
    String flightNumber,
    String origin,
    String destination,
    String aircraftId,
    LocalDateTime scheduledDep,
    LocalDateTime scheduledArr,
    int passengers,
    String gate
) {}
