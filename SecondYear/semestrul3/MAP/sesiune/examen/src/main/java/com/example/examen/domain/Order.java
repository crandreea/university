package com.example.examen.domain;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity<Integer> {
    private IntegerProperty driverId;
    private StringProperty status;
    private ObjectProperty<LocalDateTime> startDate;
    private ObjectProperty<LocalDateTime> endDate;
    private StringProperty pickupAddress;
    private StringProperty destinationAddress;
    private StringProperty clientName;

    public Order(Integer driverId, String status, LocalDateTime startDate, LocalDateTime endDate, String pickupAddress, String destinationAddress, String clientName) {
        this.driverId = new SimpleIntegerProperty(driverId);
        this.status = new SimpleStringProperty(status);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.pickupAddress = new SimpleStringProperty(pickupAddress);
        this.destinationAddress = new SimpleStringProperty(destinationAddress);
        this.clientName = new SimpleStringProperty(clientName);
    }

    public int getDriverId() {
        return driverId.get();
    }

    public IntegerProperty driverIdProperty() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId.set(driverId);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public LocalDateTime getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDateTime> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate.set(startDate);
    }

    public LocalDateTime getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDateTime> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate.set(endDate);
    }

    public String getPickupAddress() {
        return pickupAddress.get();
    }

    public StringProperty pickupAddressProperty() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress.set(pickupAddress);
    }

    public String getDestinationAddress() {
        return destinationAddress.get();
    }

    public StringProperty destinationAddressProperty() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress.set(destinationAddress);
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(driverId, order.driverId) && Objects.equals(status, order.status) && Objects.equals(startDate, order.startDate) && Objects.equals(endDate, order.endDate) && Objects.equals(pickupAddress, order.pickupAddress) && Objects.equals(destinationAddress, order.destinationAddress) && Objects.equals(clientName, order.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), driverId, status, startDate, endDate, pickupAddress, destinationAddress, clientName);
    }

    @Override
    public String toString() {
        return "Order{" + getId() +
                "driverId=" + driverId +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pickupAddress=" + pickupAddress +
                ", destinationAddress=" + destinationAddress +
                ", clientName=" + clientName +
                '}';
    }
}
