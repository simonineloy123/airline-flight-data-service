package com.airline.flightdata.domain.model;

public enum FlightStatus {
    SCHEDULED,
    BOARDING,
    DEPARTED,
    DELAYED,
    CANCELLED,
    LANDED,
    DIVERTED;

    public boolean isActive() {
        return this == SCHEDULED || this == BOARDING || this == DEPARTED || this == DELAYED;
    }

    public boolean isTerminal() {
        return this == LANDED || this == CANCELLED || this == DIVERTED;
    }

    public boolean canTransitionTo(FlightStatus next) {
        return switch (this) {
            case SCHEDULED -> next == BOARDING || next == DELAYED || next == CANCELLED;
            case BOARDING  -> next == DEPARTED || next == DELAYED || next == CANCELLED;
            case DEPARTED  -> next == LANDED   || next == DELAYED || next == DIVERTED;
            case DELAYED   -> next == BOARDING || next == DEPARTED || next == CANCELLED;
            default        -> false;
        };
    }
}
