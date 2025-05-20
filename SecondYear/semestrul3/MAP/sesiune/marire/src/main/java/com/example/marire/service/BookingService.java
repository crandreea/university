package com.example.marire.service;

import com.example.marire.domain.Booking;
import com.example.marire.domain.validators.BookingValidator;
import com.example.marire.repository.database.BookingRepository;


public class BookingService extends AbstractService<Booking, BookingRepository> {

    private static BookingService service = null;

    protected BookingService() {
        super(new BookingRepository((new BookingValidator())));
    }

    public static BookingService getInstance() {
        if (service == null) {
            service = new BookingService();
        }
        return service;
    }


}
