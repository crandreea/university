package com.example.sub1.domain.validators;

import com.example.sub1.domain.Nevoi;
import com.example.sub1.domain.Persoana;
import com.example.sub1.repository.dbRepo.PersoanaRepository;

public class NevoiValidator implements Validator<Nevoi>{

    private final PersoanaRepository persoanaRepository;

    public NevoiValidator(PersoanaRepository persoanaRepository) {
        this.persoanaRepository = persoanaRepository;
    }

    @Override
    public void validate(Nevoi entity) throws ValidationException {
        String errors = "";
        if (entity.getDescriere().isEmpty())
            errors += "Invalid user: First name can't be null";

        if (entity.getStatus().isEmpty())
            errors += "Invalid user: Last name can't be null";

        if (entity.getTitlu().isEmpty())
            errors += "Invalid user";

        if (entity.getDeadline().equals(""))
            errors += "Invalid user";


        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
