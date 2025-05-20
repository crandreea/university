package com.example.examen.domain.validators;

import com.example.examen.domain.Driver;
import com.example.examen.utils.ValidateString;

public class DriverValidator implements Validator<Driver> {
    @Override
    public void validate(Driver entity) throws ValidationException {
        if (entity == null) {
            throw new ValidationException("Must not be null");
        }

        if (!ValidateString.validateString(entity.getName())) {
            throw new ValidationException("Must be at least 3 characters");
        }
    }
}
