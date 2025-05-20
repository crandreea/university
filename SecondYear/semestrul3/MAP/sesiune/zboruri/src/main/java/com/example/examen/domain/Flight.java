package com.example.examen.domain;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight extends Entity<Long>{
    private final StringProperty from;
    private final StringProperty to;
    private final ObjectProperty<LocalDateTime> departureTime;
    private final ObjectProperty<LocalDateTime> landingTime;
    private final IntegerProperty seats;

    public Flight(String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, int seats) {
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.landingTime = new SimpleObjectProperty<>(landingTime);
        this.seats = new SimpleIntegerProperty(seats);

    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public ObjectProperty<LocalDateTime> departureTimeProperty() {
        return departureTime;
    }

    public ObjectProperty<LocalDateTime> landingTimeProperty() {
        return landingTime;
    }

    public IntegerProperty seatsProperty() {
        return seats;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return seats == flight.seats && Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(landingTime, flight.landingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, departureTime, landingTime, seats);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departureTime=" + departureTime +
                ", landingTime=" + landingTime +
                ", seats=" + seats +
                '}';
    }
}
