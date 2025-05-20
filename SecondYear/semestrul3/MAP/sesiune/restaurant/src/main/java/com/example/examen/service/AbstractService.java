package com.example.examen.service;


import com.example.examen.domain.Entity;
import com.example.examen.repository.Repository;

import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractService<ID, E extends Entity<ID>> implements Service<ID, E> {

    Repository<ID, E> repository;

    public AbstractService(Repository<ID, E> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<E> findOne(ID id) throws SQLException {
        return repository.findOne(id);
    }

    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(E entity) {
        repository.save(entity);
    }

    @Override
    public Optional<E> delete(ID id) {
        return repository.delete(id);
    }

    @Override
    public void update(E entity) {
        repository.update(entity);
    }
}
