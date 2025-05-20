package com.example.marire.repository.database;


import com.example.marire.database.DBConnection;
import com.example.marire.domain.Entity;
import com.example.marire.domain.validators.Validator;
import com.example.marire.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    protected final Connection connection;
    private final Validator<E> validator;

    public AbstractDBRepository(Validator<E> validator) {
        this.validator = validator;
        connection = DBConnection.getInstance().getConnection();
    }

    protected abstract PreparedStatement findOneQuery(ID id) throws SQLException;

    protected abstract PreparedStatement findAllQuery() throws SQLException;

    protected abstract PreparedStatement saveQuery(E entity) throws SQLException;

    protected abstract PreparedStatement updateQuery(E entity) throws SQLException;

    protected abstract E buildEntity(ResultSet resultSet) throws SQLException;

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }

        try (PreparedStatement statement = findOneQuery(id)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    E entity = buildEntity(resultSet);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Iterable<E> findAll() {
        List<E> entities = new ArrayList<>();

        try (PreparedStatement statement = findAllQuery()) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    E entity = buildEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity-ul nu poate fi null!");
        }

        validator.validate(entity);

        try (PreparedStatement statement = saveQuery(entity)) {
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    ID id = (ID) resultSet.getString(1);
                    entity.setId(id);
                }
            }
            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<E> update(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity-ul nu poate fi null!");
        }

        validator.validate(entity);

        Optional<E> entity_up = findOne(entity.getId());
        if (entity_up.isPresent()) {
            try (PreparedStatement statement = updateQuery(entity)) {
                statement.executeUpdate();
                return entity_up;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

}

