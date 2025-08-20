package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public int insert(Customer customer) throws Exception {
        String sql = "INSERT INTO customers(name, lastName, TCKN) VALUES('" +
                customer.getAdi() + "', '" +
                customer.getSoyadi() + "', '" +
                customer.getTckn() + "')";
        return SQLManager.executeUpdate(sql);

    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("TCKN")
                );
                customers.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;

    }
}