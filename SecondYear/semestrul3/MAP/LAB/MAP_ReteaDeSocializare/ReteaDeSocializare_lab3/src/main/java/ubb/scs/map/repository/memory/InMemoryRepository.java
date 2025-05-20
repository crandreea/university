package ubb.scs.map.repository.memory;


import ubb.scs.map.domain.Entity;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.domain.validators.Validator;
import ubb.scs.map.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    private final Validator<E> validator;
    protected Map<ID, E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new ValidationException("Error: Id is null");
        }
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {

        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        validator.validate(entity);
        return entities.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) {
        E entity = entities.get(id);
        if (entity == null)
            return null;
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        validator.validate(entity);
        if (!entities.containsKey(entity.getId()))
            return entity;

        entities.put(entity.getId(), entity);
        return null;
    }
}
