package org.server.service;

import model.Entity;
import persistence.Repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Optional;

public class AbstractService<ID extends Serializable, E extends Entity<ID>> implements Service<ID, E>{
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
    public void save(E entity) throws SQLException {
        repository.save(entity);
    }

    @Override
    public void delete(ID id) throws SQLException {

    }

    @Override
    public Optional<E> update(E entity) throws SQLException {
        return Optional.empty();
    }

}
