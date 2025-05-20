package com.example.marire.controller;

import com.example.marire.domain.Booking;
import com.example.marire.domain.Status;
import com.example.marire.service.BookingService;
import java.time.format.DateTimeFormatter;

import com.example.marire.utils.ParkingManager;
import com.example.marire.utils.ViewUtils;
import com.example.marire.utils.observers.AbstractObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BookingFormController extends AbstractObserver {
    public TextField licensePlateField;
    public TextField durationField;

    private final BookingService bookingService = BookingService.getInstance();
    public ListView<String> bookingsListView;

    private final Set<String> clientLicensePlates = new HashSet<>();

    private final ObservableList<String> bookings = FXCollections.observableArrayList();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML
    public void initialize() {
        bookingsListView.setItems(bookings);
        bookingService.addObserver(this);
        ParkingManager.getInstance(bookingService);
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            bookings.clear();
            bookingService.findAll().forEach(booking -> {
                if (clientLicensePlates.contains(booking.getLicensePlateNumber())) {
                    bookings.add(formatBooking(booking));
                }
            });
        });
    }


    private String formatBooking(Booking booking) {
        return String.format("ID: %d | License: %s | Duration: %d min | Status: %s | Created: %s | Start: %s | End: %s | Slot: %d",
                booking.getId(),
                booking.getLicensePlateNumber(),
                booking.getBookingTimeMinutes(),
                booking.getStatus(),
                booking.getCreationDate().format(formatter),
                booking.getBookingStartDate().format(formatter),
                booking.getBookingEndDate().format(formatter),
                booking.getParkingSlot()
        );
    }

    @FXML
    private void handleSendRequest() {
        try {
            String licensePlate = licensePlateField.getText();
            Integer duration = Integer.parseInt(durationField.getText());

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDate = now.plusMinutes(duration);

            Booking booking = new Booking(
                    licensePlate,
                    duration,
                    Status.PENDING,
                    now,
                    now,
                    endDate,
                    0
            );

            clientLicensePlates.add(licensePlate);
            bookingService.save(booking);
            bookings.add(formatBooking(booking));
            licensePlateField.clear();
            durationField.clear();

            ViewUtils.showAlert("The request has been send!");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            ViewUtils.showAlert("Wrong data!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
