package ro.mpp2025.javaprojectui.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ro.mpp2025.javaprojectui.AbstractRepository;
import ro.mpp2025.javaprojectui.CategorieVarsta;
import ro.mpp2025.javaprojectui.utils.JdbcUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategorieVarstaRepository extends AbstractRepository<Integer, CategorieVarsta> {

    private static final Logger logger= LogManager.getLogger(CategorieVarstaRepository.class);

    private final Connection connection;

    public CategorieVarstaRepository() throws SQLException {
        logger.info("Initializing CategorieVarstaRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement saveQuery(CategorieVarsta entity) throws SQLException {
        return null;
    }

    @Override
    protected CategorieVarsta buildEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    public Iterable<CategorieVarsta> findAll() {
        List<CategorieVarsta> entities = new ArrayList<>();

        logger.trace("Loading entities...");
        String query = "SELECT * FROM categorievarsta";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    int minAge = result.getInt("varstamin");
                    int maxAge = result.getInt("varstamax");

                    CategorieVarsta ageRange = new CategorieVarsta(minAge, maxAge);
                    ageRange.setId(id);

                    logger.info("Loaded entity: {}", ageRange);
                    entities.add(ageRange);
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entities: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return entities;
    }


    public Optional<CategorieVarsta> findOne(Integer id) {
        logger.trace("Loading entity with ID: {}", id);
        String query = "SELECT * FROM main.categorievarsta WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int minAge = result.getInt("varstamin");
                    int maxAge = result.getInt("varstamax");

                    CategorieVarsta ageRange = new CategorieVarsta(minAge, maxAge);
                    ageRange.setId(id);
                    logger.info("Loaded entity: {}", ageRange);
                    return Optional.of(ageRange);
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        logger.info("Entity not found with ID: {}", id);
        return Optional.empty();
    }

    public CategorieVarsta findVarstaByRange(String selectedCategory) {
        String[] ages = selectedCategory.split("-");
        int minAge = Integer.parseInt(ages[0]);
        int maxAge = Integer.parseInt(ages[1]);

        String query = "SELECT * FROM categorievarsta WHERE varstamin = ? AND varstamax = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, minAge);
            statement.setLong(2, maxAge);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    CategorieVarsta ageRange = new CategorieVarsta(minAge, maxAge);
                    ageRange.setId(result.getInt("id"));
                    logger.info("Loaded entity: {}", ageRange);
                    return ageRange;
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
