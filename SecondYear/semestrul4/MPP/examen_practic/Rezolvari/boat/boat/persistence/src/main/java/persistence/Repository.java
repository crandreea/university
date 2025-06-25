package persistence;

import model.Entity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID extends Serializable, E extends Entity<ID>> {

    Optional<E> findOne(ID id) throws SQLException;

    Iterable<E> findAll();

    Optional<E> save(E entity) throws SQLException;

}