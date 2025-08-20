package org.InvoiceUpload.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Utility class to manage SQL connections and operations
public class SQLManager {

    // Database connection details
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/upload_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    // Method to execute an SQL update (INSERT, UPDATE, DELETE)
    public static int executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return ps.executeUpdate();
        }
    }
}
