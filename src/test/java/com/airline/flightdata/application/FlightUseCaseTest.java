package com.airline.flightdata.application;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class FlightUseCaseTest {

    private Flight buildFlight(String flightNumber, String origin, String destination) {
        return Flight.builder()
            .flightNumber(flightNumber)
            .origin(origin)
            .destination(destination)
            .aircraftId("LV-TST")
            .scheduledDep(LocalDateTime.now().plusHours(2))
            .scheduledArr(LocalDateTime.now().plusHours(10))
            .passengers(150)
            .gate("A01")
            .build();
    }

    @Test
    void shouldCreateFlightWithCorrectValues() {
        Flight flight = buildFlight("LA5678", "SCL", "GRU");
        assertNotNull(flight);
        assertEquals("LA5678", flight.getFlightNumber());
        assertEquals(FlightStatus.SCHEDULED, flight.getStatus());
        assertEquals("SCL", flight.getOrigin());
        assertEquals("GRU", flight.getDestination());
        assertEquals(150, flight.getPassengers());
    }

    @Test
    void shouldSetCorrectInitialValues() {
        Flight flight = buildFlight("CM9999", "BOG", "LIM");
        assertEquals(0, flight.getDelayMinutes());
        assertFalse(flight.isDelayed());
        assertNull(flight.getActualDep());
        assertNull(flight.getActualArr());
    }

    @Test
    void shouldApplyDelayAndChangeStatus() {
        Flight flight = buildFlight("AR1111", "EZE", "MAD");
        assertEquals(FlightStatus.SCHEDULED, flight.getStatus());
        flight.applyDelay(60);
        assertEquals(FlightStatus.DELAYED, flight.getStatus());
        assertEquals(60, flight.getDelayMinutes());
        assertTrue(flight.isDelayed());
    }

    @Test
    void shouldCancelFlightCorrectly() {
        Flight flight = buildFlight("IB2222", "MAD", "BOG");
        flight.cancel();
        assertEquals(FlightStatus.CANCELLED, flight.getStatus());
    }

    @Test
    void shouldDepartFlightAndSetActualTime() {
        Flight flight = buildFlight("AA3333", "MIA", "EZE");
        flight.applyDelay(30);
        flight.depart(LocalDateTime.now());
        assertEquals(FlightStatus.DEPARTED, flight.getStatus());
        assertNotNull(flight.getActualDep());
    }
}