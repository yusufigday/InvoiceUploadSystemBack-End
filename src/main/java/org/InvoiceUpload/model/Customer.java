package org.InvoiceUpload.model;

// Model class representing a customer
public class Customer {
    private String firstName;
    private String lastName;
    private String tckn; // Turkish identification number

    // Constructor to initialize a customer
    public Customer(String firstName, String lastName, String tckn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tckn = tckn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTckn() {
        return tckn;
    }

}
