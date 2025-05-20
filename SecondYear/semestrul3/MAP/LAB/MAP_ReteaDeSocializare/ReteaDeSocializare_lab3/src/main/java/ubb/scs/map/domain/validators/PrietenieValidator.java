package ubb.scs.map.domain.validators;

import ubb.scs.map.domain.Prietenie;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.repository.memory.InMemoryRepository;

public class PrietenieValidator implements Validator<Prietenie>{

    private final InMemoryRepository<Long, Utilizator> repo;

    public PrietenieValidator(InMemoryRepository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        Utilizator u1 = repo.findOne(entity.getIdUser1());
        Utilizator u2 = repo.findOne(entity.getIdUser2());

        if (entity.getIdUser1() == null || entity.getIdUser2() == null)
            throw new ValidationException("Id cannot be null");
        if (u1 == null || u2 == null)
            throw new ValidationException("Id doesn't exist");
    }
}
