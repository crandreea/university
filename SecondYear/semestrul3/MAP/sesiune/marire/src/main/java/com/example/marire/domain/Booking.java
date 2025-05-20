package com.example.marire.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Booking extends Entity<Integer>{
    private String licensePlateNumber;
    private Integer bookingTimeMinutes;
    private Status status;
    private LocalDateTime creationDate;
    private LocalDateTime bookingStartDate;
    private LocalDateTime bookingEndDate;
    private Integer parkingSlot;

    public Booking(String licensePlateNumber, Integer bookingTimeMinutes, Status status, LocalDateTime creationDate, LocalDateTime bookingStartDate, LocalDateTime bookingEndDate, Integer parkingSlot) {
        this.licensePlateNumber = licensePlateNumber;
        this.bookingTimeMinutes = bookingTimeMinutes;
        this.status = status;
        this.creationDate = creationDate;
        this.bookingStartDate = bookingStartDate;
        this.bookingEndDate = bookingEndDate;
        this.parkingSlot = parkingSlot;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }


    public Integer getBookingTimeMinutes() {
        return bookingTimeMinutes;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(LocalDateTime bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public LocalDateTime getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(LocalDateTime bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public Integer getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(Integer parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(licensePlateNumber, booking.licensePlateNumber) && Objects.equals(bookingTimeMinutes, booking.bookingTimeMinutes) && status == booking.status && Objects.equals(creationDate, booking.creationDate) && Objects.equals(bookingStartDate, booking.bookingStartDate) && Objects.equals(bookingEndDate, booking.bookingEndDate) && Objects.equals(parkingSlot, booking.parkingSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlateNumber, bookingTimeMinutes, status, creationDate, bookingStartDate, bookingEndDate, parkingSlot);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "licensePlateNumber='" + licensePlateNumber + '\'' +
                ", bookingTimeMinutes=" + bookingTimeMinutes +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", bookingStartDate=" + bookingStartDate +
                ", bookingEndDate=" + bookingEndDate +
                ", parkingSlot=" + parkingSlot +
                '}';
    }
}
