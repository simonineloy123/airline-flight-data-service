package com.airline.flightdata.domain.port.in;

import com.airline.flightdata.domain.model.Flight;

public interface CreateFlightUseCase {
    Flight execute(CreateFlightCommand command);
}
