package repo;

import domain.Entity;
import domain.validators.Validator;
import domain.validators.ValidationException;

import java.util.HashMap;
import java.util.Map;


public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    Map<ID, E> entities = new HashMap<>();
    Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public E findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return null;
    }

    @Override
    public E save(E entity){
        if(entity == null)
            throw new IllegalArgumentException();

        validator.validate(entity);
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public E delete(ID id) {
        return null;
    }

    @Override
    public E update(E entity) {
        return null;
    }
}
