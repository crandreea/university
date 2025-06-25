package ro.mpp2025.javaprojectui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    private final static Logger logger = LogManager.getLogger(AbstractRepository.class);

    public AbstractRepository() {
    }

    protected abstract PreparedStatement findOneQuery(ID id) throws SQLException;

    protected abstract PreparedStatement findAllQuery() throws SQLException;

    protected abstract PreparedStatement saveQuery(E entity) throws SQLException;

    protected abstract E buildEntity(ResultSet resultSet) throws SQLException;


    @Override
    public Optional<E> findOne(ID id) {
        logger.trace("Loading entity with ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }

        try (PreparedStatement statement = findOneQuery(id)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    E entity = buildEntity(resultSet);
                    entity.setId(id);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        logger.info("Entity not found with ID: {}", id);
        return Optional.empty();
    }

    @Override
    public Iterable<E> findAll() {
        logger.trace("Loading entities...");
        List<E> entities = new ArrayList<>();

        try (PreparedStatement statement = findAllQuery()) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    E entity = buildEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entities: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return entities;
    }

    @Override
    public Optional<E> save(E entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity-ul nu poate fi null!");
        }
        logger.trace("Saving entity: {}", entity);
        try (PreparedStatement statement = saveQuery(entity)) {
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    ID id = (ID) resultSet.getObject(1);
                    entity.setId(id);
                }
            }
            return Optional.of(entity);
        } catch (SQLException e) {
            logger.error("Error saving entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
