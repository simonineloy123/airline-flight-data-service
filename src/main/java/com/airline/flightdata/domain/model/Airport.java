package com.airline.flightdata.domain.model;

public class Airport {

    private String id;
    private String name;
    private String city;
    private String country;
    private String timezone;
    private Double latitude;
    private Double longitude;

    private Airport() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Airport airport = new Airport();

        public Builder id(String id) { airport.id = id; return this; }
        public Builder name(String name) { airport.name = name; return this; }
        public Builder city(String city) { airport.city = city; return this; }
        public Builder country(String country) { airport.country = country; return this; }
        public Builder timezone(String timezone) { airport.timezone = timezone; return this; }
        public Builder latitude(Double latitude) { airport.latitude = latitude; return this; }
        public Builder longitude(Double longitude) { airport.longitude = longitude; return this; }

        public Airport build() {
            validate();
            return airport;
        }

        private void validate() {
            if (airport.id == null || airport.id.isBlank())
                throw new IllegalArgumentException("El código IATA es obligatorio");
            if (airport.id.length() != 3)
                throw new IllegalArgumentException("El código IATA debe tener 3 caracteres");
            if (airport.name == null || airport.name.isBlank())
                throw new IllegalArgumentException("El nombre del aeropuerto es obligatorio");
            if (airport.city == null || airport.city.isBlank())
                throw new IllegalArgumentException("La ciudad es obligatoria");
            if (airport.country == null || airport.country.isBlank())
                throw new IllegalArgumentException("El país es obligatorio");
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getTimezone() { return timezone; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
}
