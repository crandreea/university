package com.example.sub1.repository.dbRepo;

import com.example.sub1.database.DBConnection;
import com.example.sub1.domain.Nevoi;
import com.example.sub1.domain.validators.NevoiValidator;
import com.example.sub1.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NevoiRepository extends AbstractRepo<Long, Nevoi>{

    private final Connection connection;

    public NevoiRepository(NevoiValidator validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long aLong) throws SQLException {
        String query = "SELECT * FROM nevoi WHERE id_n = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM nevoi";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(Nevoi entity) throws SQLException {
        String query = "INSERT INTO nevoi(titlu, descriere, status, deadline, ominnevoie, omsalvator) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getTitlu());
        statement.setObject(2, entity.getOmInNevoie());
        statement.setObject(3, entity.getStatus());
        statement.setObject(4, entity.getDeadline());
        statement.setObject(5, entity.getOmInNevoie());
        statement.setObject(6, entity.getOmSalvator());

        return statement;
    }

    @Override
    protected PreparedStatement deleteQuery(Long aLong) throws SQLException {
        String query = "DELETE FROM nevoi WHERE id_n = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, aLong);

        return statement;
    }

    @Override
    protected PreparedStatement updateQuery(Nevoi entity) throws SQLException {
        String query = "UPDATE nevoi SET titlu = ?, descriere = ?, deadline = ?, ominnevoie = ?, omsalvator = ?, " +
                "status = ? WHERE id_n = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getTitlu());
        statement.setString(2, entity.getDescriere());
        statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDeadline()));
        statement.setLong(4, entity.getOmInNevoie());
        statement.setLong(5, entity.getOmSalvator());
        statement.setString(6, entity.getStatus());
        statement.setLong(7, entity.getId());
        return statement;
    }

    public PreparedStatement findAllByOrasQuery(String oras) throws SQLException {
        String query = "SELECT * FROM nevoi n INNER JOIN persoane p ON n.ominnevoie = p.id_p WHERE p.oras = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, oras);
        return statement;
    }

    public Iterable<Nevoi> findAllbyOras(String oras) {
        List<Nevoi> entities = new ArrayList<>();

        try (PreparedStatement statement = findAllByOrasQuery(oras)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Nevoi entity = buildEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entities;
    }

    @Override
    protected Nevoi buildEntity(ResultSet resultSet) throws SQLException {
        Long uid1 = resultSet.getLong("id_n");
        String titlu = resultSet.getString("titlu");
        String descriere = resultSet.getString("descriere");
        String status = resultSet.getString("status");
        LocalDateTime deadline = LocalDateTime.parse(resultSet.getString("deadline"));
        Long ominnevoie = resultSet.getLong("ominnevoie");
        Long omsalvator = resultSet.getLong("omsalvator");

        Nevoi nevoi = new Nevoi(titlu, descriere, status, deadline, ominnevoie, omsalvator);
        nevoi.setId(uid1);
        return nevoi;
    }


}
