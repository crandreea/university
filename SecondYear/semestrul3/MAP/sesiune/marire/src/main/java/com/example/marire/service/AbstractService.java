package com.example.marire.service;


import com.example.marire.domain.Entity;
import com.example.marire.repository.Repository;
import com.example.marire.utils.observers.AbstractObservable;

import java.util.Optional;

public abstract class AbstractService<E extends Entity<Integer>, R extends Repository<Integer, E>> extends AbstractObservable {

    protected R repository;

    protected AbstractService(R repository) {
        this.repository = repository;
    }

    public Iterable<E> findAll() {
        return repository.findAll();
    }

    public void save(E entity) {
        repository.save(entity);
        notifyObservers();
    }

    public Optional<E> update(E entity) {
        Optional<E> updated = repository.update(entity);
        notifyObservers();
        return updated;
    }
}
