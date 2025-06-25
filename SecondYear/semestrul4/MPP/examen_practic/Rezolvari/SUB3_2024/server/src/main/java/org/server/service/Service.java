package org.server.service;

import model.Entity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Optional;

public interface Service<ID extends Serializable, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException;

    Iterable<E> findAll();

    void save(E entity) throws SQLException;

    void delete(ID id) throws SQLException;

    Optional<E> update(E entity) throws SQLException;

}
