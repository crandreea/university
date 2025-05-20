package com.example.examen.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket extends Entity<Long> {
    private SimpleStringProperty username;
    private SimpleLongProperty flightId;
    private SimpleObjectProperty<LocalDateTime> purchaseTime;

    public Ticket(String username, Long flightId, LocalDateTime purchaseTime) {
        this.username = new SimpleStringProperty(username);
        this.flightId = new SimpleLongProperty(flightId);
        this.purchaseTime = new SimpleObjectProperty<>(purchaseTime);
    }

    // Getters for the Simple properties (for binding in JavaFX)
    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public Long getFlightId() {
        return flightId.get();
    }

    public SimpleLongProperty flightIdProperty() {
        return flightId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> purchaseTimeProperty() {
        return purchaseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(username.get(), ticket.username.get()) &&
                Objects.equals(flightId.get(), ticket.flightId.get()) &&
                Objects.equals(purchaseTime.get(), ticket.purchaseTime.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username.get(), flightId.get(), purchaseTime.get());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "username='" + username.get() + '\'' +
                ", flightId=" + flightId.get() +
                ", purchaseTime=" + purchaseTime.get() +
                '}';
    }
}
