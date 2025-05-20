package com.example.sub1.repository;


import com.example.sub1.domain.Entity;

import java.sql.SQLException;
import java.util.Optional;


public interface Repository<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException;
    Iterable<E> findAll();
    Optional<E> save(E entity);
    Optional<E> delete(ID id);
    Optional<E> update(E entity);
}
