package com.airline.flightdata.domain;

import com.airline.flightdata.domain.model.Flight;
import com.airline.flightdata.domain.model.FlightStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight buildFlight() {
        return Flight.builder()
            .flightNumber("AR1234")
            .origin("EZE")
            .destination("MAD")
            .aircraftId("LV-HKP")
            .scheduledDep(LocalDateTime.now().plusHours(2))
            .scheduledArr(LocalDateTime.now().plusHours(14))
            .passengers(200)
            .gate("A01")
            .build();
    }

    @Test
    void shouldCreateFlightWithDefaultStatus() {
        Flight flight = buildFlight();
        assertEquals(FlightStatus.SCHEDULED, flight.getStatus());
        assertFalse(flight.isDelayed());
        assertEquals(0, flight.getDelayMinutes());
        assertNotNull(flight.getId());
    }

    @Test
    void shouldApplyDelayCorrectly() {
        Flight flight = buildFlight();
        flight.applyDelay(90);
        assertEquals(FlightStatus.DELAYED, flight.getStatus());
        assertEquals(90, flight.getDelayMinutes());
        assertTrue(flight.isDelayed());
    }

    @Test
    void shouldCancelFlight() {
        Flight flight = buildFlight();
        flight.cancel();
        assertEquals(FlightStatus.CANCELLED, flight.getStatus());
    }

    @Test
    void shouldDepartFlight() {
        Flight flight = buildFlight();
        flight.applyDelay(30); 
        flight.depart(LocalDateTime.now()); 
        assertEquals(FlightStatus.DEPARTED, flight.getStatus());
        assertNotNull(flight.getActualDep());
    }

    @Test
    void shouldNotAllowNullFlightNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            Flight.builder()
                .origin("EZE")
                .destination("MAD")
                .aircraftId("LV-HKP")
                .scheduledDep(LocalDateTime.now())
                .scheduledArr(LocalDateTime.now().plusHours(5))
                .passengers(100)
                .gate("A01")
                .build()
        );
    }

    @Test
    void shouldCalculateIsDelayedCorrectly() {
        Flight flight = buildFlight();
        assertFalse(flight.isDelayed());
        flight.applyDelay(30);
        assertTrue(flight.isDelayed());
    }
}