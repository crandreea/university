package ro.mpp2025.javaprojectui.interfaces;


import ro.mpp2025.javaprojectui.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IProbaRepository extends Repository<Integer, Proba> {
    public PreparedStatement getProbaByName(String name) throws SQLException;
    public PreparedStatement getProbaByNameAndRange(String name, Integer range) throws SQLException;
    public PreparedStatement updateProba(Proba proba) throws SQLException;
    public PreparedStatement deleteProba(Integer range) throws SQLException;
}
