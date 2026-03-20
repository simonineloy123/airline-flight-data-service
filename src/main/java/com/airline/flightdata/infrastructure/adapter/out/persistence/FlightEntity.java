package com.airline.flightdata.infrastructure.adapter.out.persistence;

import com.airline.flightdata.domain.model.FlightStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "flights")
public class FlightEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "flight_number", nullable = false, unique = true, length = 10)
    public String flightNumber;

    @Column(name = "origin", nullable = false, length = 10)
    public String origin;

    @Column(name = "destination", nullable = false, length = 10)
    public String destination;

    @Column(name = "aircraft_id", length = 20)
    public String aircraftId;

    @Column(name = "scheduled_dep", nullable = false)
    public LocalDateTime scheduledDep;

    @Column(name = "scheduled_arr", nullable = false)
    public LocalDateTime scheduledArr;

    @Column(name = "actual_dep")
    public LocalDateTime actualDep;

    @Column(name = "actual_arr")
    public LocalDateTime actualArr;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    public FlightStatus status;

    @Column(name = "delay_minutes", nullable = false)
    public int delayMinutes;

    @Column(name = "passengers", nullable = false)
    public int passengers;

    @Column(name = "gate", length = 10)
    public String gate;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = FlightStatus.SCHEDULED;
        if (delayMinutes < 0) delayMinutes = 0;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Optional<FlightEntity> findByFlightNumber(String flightNumber) {
        return find("flightNumber", flightNumber).firstResultOptional();
    }

    public static List<FlightEntity> findByStatus(FlightStatus status) {
        return list("status", status);
    }

    public static List<FlightEntity> findByOrigin(String iataCode) {
        return list("origin", iataCode);
    }

    public static List<FlightEntity> findByDestination(String iataCode) {
        return list("destination", iataCode);
    }

    public static boolean existsByFlightNumber(String flightNumber) {
        return count("flightNumber", flightNumber) > 0;
    }
}
