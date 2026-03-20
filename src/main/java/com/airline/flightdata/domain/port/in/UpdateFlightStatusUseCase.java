package com.airline.flightdata.domain.port.in;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import java.util.UUID;

public interface UpdateFlightStatusUseCase {
    Flight execute(UUID flightId, FlightStatus newStatus, int delayMinutes);
}
