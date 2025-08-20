package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Customer;

import java.util.List;

// Repository interface for Customer entity
public interface CustomerRepository {

    // Inserts a new customer into the database
    int insert(Customer customer) throws Exception;

    // Retrieves all customers from the database
    List<Customer> getAllCustomers();
}
