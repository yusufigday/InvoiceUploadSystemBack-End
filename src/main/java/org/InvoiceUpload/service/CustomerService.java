package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Customer;
import org.InvoiceUpload.repository.CustomerRepository;
import org.InvoiceUpload.repository.CustomerRepositoryImpl;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();

    public boolean addCustomer(Customer customer) {
        try {
            return customerRepository.insert(customer) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

}
