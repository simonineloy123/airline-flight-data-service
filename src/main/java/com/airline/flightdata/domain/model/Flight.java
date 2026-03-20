package com.airline.flightdata.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Flight {

    private UUID id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String aircraftId;
    private LocalDateTime scheduledDep;
    private LocalDateTime scheduledArr;
    private LocalDateTime actualDep;
    private LocalDateTime actualArr;
    private FlightStatus status;
    private int delayMinutes;
    private int passengers;
    private String gate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Flight() {}


    public void applyDelay(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Los minutos de retraso no pueden ser negativos");
        }
        this.delayMinutes = minutes;
        if (minutes > 0 && this.status.canTransitionTo(FlightStatus.DELAYED)) {
            this.status = FlightStatus.DELAYED;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (!this.status.canTransitionTo(FlightStatus.CANCELLED)) {
            throw new IllegalStateException(
                "No se puede cancelar un vuelo en estado: " + this.status
            );
        }
        this.status = FlightStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void depart(LocalDateTime actualDepartureTime) {
        if (!this.status.canTransitionTo(FlightStatus.DEPARTED)) {
            throw new IllegalStateException(
                "No se puede partir un vuelo en estado: " + this.status
            );
        }
        this.actualDep = actualDepartureTime;
        this.status = FlightStatus.DEPARTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void land(LocalDateTime actualArrivalTime) {
        if (!this.status.canTransitionTo(FlightStatus.LANDED)) {
            throw new IllegalStateException(
                "No se puede aterrizar un vuelo en estado: " + this.status
            );
        }
        this.actualArr = actualArrivalTime;
        this.status = FlightStatus.LANDED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isDelayed() {
        return this.delayMinutes > 0 || this.status == FlightStatus.DELAYED;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Flight flight = new Flight();

        public Builder id(UUID id) { flight.id = id; return this; }
        public Builder flightNumber(String flightNumber) { flight.flightNumber = flightNumber; return this; }
        public Builder origin(String origin) { flight.origin = origin; return this; }
        public Builder destination(String destination) { flight.destination = destination; return this; }
        public Builder aircraftId(String aircraftId) { flight.aircraftId = aircraftId; return this; }
        public Builder scheduledDep(LocalDateTime scheduledDep) { flight.scheduledDep = scheduledDep; return this; }
        public Builder scheduledArr(LocalDateTime scheduledArr) { flight.scheduledArr = scheduledArr; return this; }
        public Builder actualDep(LocalDateTime actualDep) { flight.actualDep = actualDep; return this; }
        public Builder actualArr(LocalDateTime actualArr) { flight.actualArr = actualArr; return this; }
        public Builder status(FlightStatus status) { flight.status = status; return this; }
        public Builder delayMinutes(int delayMinutes) { flight.delayMinutes = delayMinutes; return this; }
        public Builder passengers(int passengers) { flight.passengers = passengers; return this; }
        public Builder gate(String gate) { flight.gate = gate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { flight.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { flight.updatedAt = updatedAt; return this; }

        public Flight build() {
            validate();
            if (flight.id == null) flight.id = UUID.randomUUID();
            if (flight.status == null) flight.status = FlightStatus.SCHEDULED;
            if (flight.createdAt == null) flight.createdAt = LocalDateTime.now();
            if (flight.updatedAt == null) flight.updatedAt = LocalDateTime.now();
            return flight;
        }

        private void validate() {
            if (flight.flightNumber == null || flight.flightNumber.isBlank())
                throw new IllegalArgumentException("El número de vuelo es obligatorio");
            if (flight.origin == null || flight.origin.isBlank())
                throw new IllegalArgumentException("El origen es obligatorio");
            if (flight.destination == null || flight.destination.isBlank())
                throw new IllegalArgumentException("El destino es obligatorio");
            if (flight.origin.equals(flight.destination))
                throw new IllegalArgumentException("El origen y destino no pueden ser iguales");
            if (flight.scheduledDep == null)
                throw new IllegalArgumentException("La fecha de salida es obligatoria");
            if (flight.scheduledArr == null)
                throw new IllegalArgumentException("La fecha de llegada es obligatoria");
            if (!flight.scheduledArr.isAfter(flight.scheduledDep))
                throw new IllegalArgumentException("La llegada debe ser posterior a la salida");
        }
    }

    public UUID getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getAircraftId() { return aircraftId; }
    public LocalDateTime getScheduledDep() { return scheduledDep; }
    public LocalDateTime getScheduledArr() { return scheduledArr; }
    public LocalDateTime getActualDep() { return actualDep; }
    public LocalDateTime getActualArr() { return actualArr; }
    public FlightStatus getStatus() { return status; }
    public int getDelayMinutes() { return delayMinutes; }
    public int getPassengers() { return passengers; }
    public String getGate() { return gate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
