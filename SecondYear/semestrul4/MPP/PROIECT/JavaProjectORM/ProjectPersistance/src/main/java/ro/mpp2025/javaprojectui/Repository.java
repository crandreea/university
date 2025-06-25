package ro.mpp2025.javaprojectui;


import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id);

    Iterable<E> findAll();

    Optional<E> save(E entity) throws SQLException;

}