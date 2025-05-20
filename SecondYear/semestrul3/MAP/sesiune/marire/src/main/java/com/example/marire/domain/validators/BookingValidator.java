package com.example.marire.domain.validators;

import com.example.marire.domain.Booking;

public class BookingValidator implements Validator<Booking> {
    @Override
    public void validate(Booking entity) throws ValidationException {
        if(entity == null) {
            throw new ValidationException("Booking is null");
        }
    }
}
