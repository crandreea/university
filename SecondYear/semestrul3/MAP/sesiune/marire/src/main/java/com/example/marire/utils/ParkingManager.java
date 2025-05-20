package com.example.marire.utils;

import com.example.marire.domain.Booking;
import com.example.marire.domain.Status;
import com.example.marire.service.BookingService;
import javafx.application.Platform;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ParkingManager {
    private static ParkingManager instance;
    private final BookingService bookingService;
    private final int totalParkingSlots = 2;
    private final Set<Integer> occupiedSlots = new HashSet<>();
    private final Timer timer;

    private ParkingManager(BookingService bookingService) {
        this.bookingService = bookingService;
        this.timer = new Timer(true);
        startMonitoring();
    }

    public static ParkingManager getInstance(BookingService bookingService) {
        if (instance == null) {
            instance = new ParkingManager(bookingService);
        }
        return instance;
    }

    private void startMonitoring() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    processExpiredBookings();
                    processNewBookings();
                });
            }
        }, 0, 5000);
    }

    private void processExpiredBookings() {
        List<Booking> bookedBookings = StreamSupport.stream(bookingService.findAll().spliterator(), false)
                .filter(b -> b.getStatus() == Status.BOOKED)
                .collect(Collectors.toList());

        for (Booking booking : bookedBookings) {
            if (booking.getBookingEndDate().isBefore(LocalDateTime.now())) {
                booking.setStatus(Status.EXITED);
                booking.setBookingEndDate(LocalDateTime.now());
                bookingService.update(booking);
                occupiedSlots.remove(booking.getParkingSlot());
            }
        }
    }

    private void processNewBookings() {
        if (occupiedSlots.size() >= totalParkingSlots) {
            return;
        }

        List<Booking> pendingBookings = StreamSupport.stream(bookingService.findAll().spliterator(), false)
                .filter(b -> b.getStatus() == Status.PENDING)
                .sorted(Comparator.comparing(Booking::getCreationDate))
                .collect(Collectors.toList());

        for (Booking booking : pendingBookings) {
            int availableSlot = findAvailableSlot();
            System.out.println(availableSlot);
            if (availableSlot == -1) break;

            booking.setStatus(Status.BOOKED);
            booking.setParkingSlot(availableSlot);
            booking.setBookingStartDate(LocalDateTime.now());
            booking.setBookingEndDate(LocalDateTime.now().plusMinutes(booking.getBookingTimeMinutes()));

            bookingService.update(booking);
            occupiedSlots.add(availableSlot);
        }
    }

    private int findAvailableSlot() {
        for (int i = 1; i <= totalParkingSlots; i++) {
            if (!occupiedSlots.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    public void shutdown() {
        timer.cancel();
    }

}
