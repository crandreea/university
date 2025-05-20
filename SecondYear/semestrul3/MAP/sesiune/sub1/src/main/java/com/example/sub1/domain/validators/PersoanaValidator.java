package com.example.sub1.domain.validators;

import com.example.sub1.domain.Persoana;

public class PersoanaValidator implements Validator<Persoana>{

    @Override
    public void validate(Persoana entity) throws ValidationException {
        String errors = "";
        if (entity.getUsername().isEmpty())
            errors += "Invalid user: First name can't be null";

        if (entity.getPassword().isEmpty())
            errors += "Invalid user: Last name can't be null";

        if (entity.getName().isEmpty())
            errors += "Invalid user";

        if (entity.getSurname().isEmpty())
            errors += "Invalid user";

        if (entity.getStrada().isEmpty())
            errors += "Invalid user";

        if (entity.getTelefon().isEmpty())
            errors += "Invalid user";

        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}