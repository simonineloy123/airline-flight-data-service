package com.airline.flightdata.infrastructure.adapter.out.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class AirportEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", length = 10, nullable = false)
    public String id;

    @Column(name = "name", nullable = false, length = 120)
    public String name;

    @Column(name = "city", nullable = false, length = 80)
    public String city;

    @Column(name = "country", nullable = false, length = 80)
    public String country;

    @Column(name = "timezone", nullable = false, length = 50)
    public String timezone;

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longitude")
    public Double longitude;
}
