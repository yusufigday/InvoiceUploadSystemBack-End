package org.InvoiceUpload.db;

import java.sql.*;

public class SQLManager {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/upload_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    public static int executeUpdate(String sql) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

}
