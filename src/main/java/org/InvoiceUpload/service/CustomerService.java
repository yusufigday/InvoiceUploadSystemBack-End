package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Customer;
import org.InvoiceUpload.repository.CustomerRepository;
import org.InvoiceUpload.repository.CustomerRepositoryImpl;

import java.util.List;

// Service layer for customer-related operations
public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();

    // Adds a new customer using the repository
    public boolean addCustomer(Customer customer) {
        try {
            return customerRepository.insert(customer) > 0; // Return true if insertion affected rows
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception
            return false;
        }
    }

    // Retrieves all customers from repository
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }
}
