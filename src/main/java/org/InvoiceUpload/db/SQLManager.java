package org.InvoiceUpload.db;

import java.sql.*;

public class SQLManager {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/sampledb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = getConnection();
        if (conn != null) {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();

        }
        return null;
    }

    public static int executeUpdate(String sql) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    public ResultSet selectUser(String query) throws SQLException {
        ResultSet rs = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id_product");
                String recipeSeriNo = rs.getString("recipeSeriNo");
                String musteriNo = rs.getString("MusteriNo");
                String urunNo = rs.getString("UrunNo");

                System.out.println("ID: " + id + ", Name: " + recipeSeriNo + ",Fiyatı:" + musteriNo + ", Miktarı:" + urunNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return executeQuery(query);

    }

    public int updateUser(String query) throws SQLException {
        return executeUpdate(query);
    }
}
