//package ro.mpp2025.javaprojectui.database;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import ro.mpp2025.javaprojectui.AbstractRepository;
//import ro.mpp2025.javaprojectui.Organizator;
//import ro.mpp2025.javaprojectui.interfaces.IOrganizatorRepository;
//import ro.mpp2025.javaprojectui.utils.JdbcUtils;
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class OrganizatorRepository extends AbstractRepository<Integer, Organizator> implements IOrganizatorRepository {
//
//    private static final Logger logger= LogManager.getLogger(OrganizatorRepository.class);
//
//    private final Connection connection;
//
//    public OrganizatorRepository() throws SQLException {
//        logger.info("Initializing OrganizatorRepository");
//        this.connection = JdbcUtils.getInstance().getConnection();
//    }
//
//    @Override
//    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
//        String query = "SELECT * FROM organizatori WHERE id = ?";
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setObject(1, id);
//
//        return statement;
//    }
//
//    @Override
//    protected PreparedStatement findAllQuery() throws SQLException {
//        String query = "SELECT * FROM organizatori";
//        return connection.prepareStatement(query);
//    }
//
//    @Override
//    protected PreparedStatement saveQuery(Organizator entity) throws SQLException {
//        String query = "INSERT INTO organizatori(id, username, password) VALUES (?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setObject(1, entity.getId());
//        statement.setString(2, entity.getUsername());
//        statement.setString(3, entity.getPassword());
//        return statement;
//    }
//
//    @Override
//    protected Organizator buildEntity(ResultSet resultSet) throws SQLException {
//        Integer id = resultSet.getInt("id");
//        String username = resultSet.getString("username");
//        String password= resultSet.getString("password");
//
//        Organizator organizator = new Organizator(username, password);
//        organizator.setId(id);
//
//        return organizator;
//    }
//
//}
