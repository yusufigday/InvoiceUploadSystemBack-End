package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Customer;

import java.util.List;

public interface CustomerRepository {
    int insert(Customer customer) throws Exception;
    List<Customer> getAllCustomers();
}