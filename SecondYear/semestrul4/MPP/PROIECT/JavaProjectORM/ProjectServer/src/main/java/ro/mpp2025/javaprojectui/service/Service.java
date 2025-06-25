package ro.mpp2025.javaprojectui.service;

import ro.mpp2025.javaprojectui.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Service <ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException;

    Iterable<E> findAll();

    void save(E entity) throws SQLException;

    void delete(ID id) throws SQLException;

    Optional<E> update(E entity) throws SQLException;

}
