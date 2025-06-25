package ro.mpp2025.javaprojectui.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.interfaces.IProbaRepository;
import ro.mpp2025.javaprojectui.utils.JdbcUtils;

import java.sql.*;
import java.util.Optional;

@Repository
@Component
public class ProbaRepository extends AbstractRepository<Integer, Proba> implements IProbaRepository {

    private static final Logger logger= LogManager.getLogger(ProbaRepository.class);

    private final Connection connection;

    public ProbaRepository() throws SQLException {
        logger.info("Initializing CarsDBRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM probe " +
                "INNER JOIN categorievarsta on probe.varsta = categorievarsta.id" +
                " WHERE proba_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM probe" +
                " INNER JOIN categorievarsta on probe.varsta = categorievarsta.id";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Proba entity) throws SQLException {
        String query = "INSERT INTO probe(tip, varsta) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getTip());
        statement.setObject(2, entity.getVarsta().getId());
        return statement;
    }

    @Override
    protected Proba buildEntity(ResultSet resultSet) throws SQLException {

        Integer id = resultSet.getInt("proba_id");
        String tip = resultSet.getString("tip");
        Integer varstaId = resultSet.getInt("varsta");

        CategorieVarstaRepository cvRepo = new CategorieVarstaRepository();
        Optional<CategorieVarsta> varstaOp =  cvRepo.findOne(varstaId);

        if (varstaOp.isPresent()) {
            CategorieVarsta varsta = varstaOp.get();
            Proba proba = new Proba(tip, varsta);
            proba.setId(id);
            return proba;
        } else {
            throw new RuntimeException("CategorieVarsta not found for id: " + varstaId);
        }

    }

    @Override
    public PreparedStatement getProbaByName(String name) throws SQLException {
        String query = "SELECT * FROM probe WHERE tip = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);

        return statement;
    }

    @Override
    public PreparedStatement getProbaByNameAndRange(String name, Integer range) throws SQLException {
        String query = "SELECT * FROM probe WHERE tip = ? AND varsta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        statement.setInt(2, range);

        return statement;
    }

    @Override
    public PreparedStatement updateProba(Proba entity) throws SQLException {
        String query = "UPDATE probe SET tip = ?, varsta = ? WHERE proba_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getTip());
        statement.setObject(2, entity.getVarsta().getId());
        statement.setObject(3, entity.getId());
        return statement;
    }

    @Override
    public PreparedStatement deleteProba(Integer id) throws SQLException {
        String query = "DELETE FROM probe WHERE proba_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);
        return statement;
    }

    @Override
    public Optional<Proba> save(Proba entity) throws SQLException {
        try(PreparedStatement statement = saveQuery(entity)){
            int result = statement.executeUpdate();
            if(result > 0){
                try(Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery("SELECT last_insert_rowid()");){
                    if(resultSet.next()){
                        entity.setId(resultSet.getInt(1));
                    }
                    else{
                        throw new SQLException("Creating proba failed, no ID obtained.");
                    }
                }
                return Optional.of(entity);
            }
        } catch (Exception e) {
            System.out.println("Eroare DB:" + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Proba> update(Proba entity) throws SQLException {
        try (PreparedStatement statement =  updateProba(entity)) {
            int result = statement.executeUpdate();
            logger.info("Updated proba: " + entity);
            if(result > 0 ){
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            logger.error("Error update proba: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = deleteProba(id)) {
            int result = statement.executeUpdate();
            logger.info("Deleted proba with id: " + id);
        } catch (SQLException e) {
            logger.error("Error delete proba: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public Proba findProbaByName(String name) {

        try (PreparedStatement statement =  getProbaByName(name)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Proba entity = buildEntity(resultSet);
                    entity.setId(resultSet.getInt("proba_id"));
                    return entity;
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public Proba findProbaByNameAndRange(String name, Integer range) {
        try (PreparedStatement statement =  getProbaByNameAndRange(name, range)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Proba entity = buildEntity(resultSet);
                    entity.setId(resultSet.getInt("proba_id"));
                    return entity;
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }


}
