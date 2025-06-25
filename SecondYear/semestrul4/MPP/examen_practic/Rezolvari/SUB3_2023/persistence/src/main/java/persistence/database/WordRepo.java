package persistence.database;

import model.User;
import model.Word;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IWordRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordRepo extends AbstractRepository<Integer, Word> implements IWordRepo {

    private static final Logger logger= LogManager.getLogger(WordRepo.class);

    private final Connection connection;

    public WordRepo() throws SQLException {
        logger.info("Initializing OrganizatorRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM words WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM words";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Word entity) throws SQLException {
        String query = "INSERT INTO words(word) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getWord());
        return statement;
    }

    @Override
    protected Word buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String username = resultSet.getString("word");

        Word word = new Word(username);
        word.setId(id);

        return word;
    }
}
