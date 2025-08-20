package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Implementation of CustomerRepository interface
public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public int insert(Customer customer) throws Exception {
        // SQL statement to insert a new customer
        String sql = "INSERT INTO customers(name, lastName, TCKN) VALUES('" +
                customer.getFirstName() + "', '" +
                customer.getLastName() + "', '" +
                customer.getTckn() + "')";
        return SQLManager.executeUpdate(sql); // Executes the insert and returns affected rows
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        // Try-with-resources ensures connections are closed automatically
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Map each row to a Customer object
                Customer customer = new Customer(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("TCKN")
                );
                customers.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace on error
        }

        return customers; // Return the list of customers
    }
}
