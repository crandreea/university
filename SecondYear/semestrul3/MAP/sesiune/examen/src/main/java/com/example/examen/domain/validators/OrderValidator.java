package com.example.examen.domain.validators;

import com.example.examen.domain.Order;
import com.example.examen.utils.ValidateString;

public class OrderValidator implements Validator<Order> {

    @Override
    public void validate(Order entity) throws ValidationException {
        if (entity == null) {
            throw new ValidationException("Must not be null");
        }

        if (!ValidateString.validateString(entity.getClientName())) {
            throw new ValidationException("Must be at least 3 characters");
        }
    }
}
