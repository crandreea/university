package com.example.sub1.service;


import com.example.sub1.domain.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Service<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException;

    Iterable<E> findAll();

    void save(E entity);

    Optional<E> delete(ID id);

    void update(E entity);
}