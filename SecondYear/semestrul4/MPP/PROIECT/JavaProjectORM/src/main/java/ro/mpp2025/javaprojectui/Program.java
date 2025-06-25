package ro.mpp2025.javaprojectui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:/Users/croitoruandreea/Desktop/ANUL2/semestrul4/MPP/PROIECT/mpp-proiect-java-crandreea/JavaProjectORM/concurs";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexiune la baza de date SQLite reușită!");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la conectarea la baza de date SQLite: " + e.getMessage());
        }
    }
}
