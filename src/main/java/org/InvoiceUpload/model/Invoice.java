package org.InvoiceUpload.model;

// Model class representing an invoice
public class Invoice {
    private int idInvoice; // Unique ID of the invoice
    private String date; // Date of the invoice
    private String invoiceSerialNumber; // Serial number of the invoice
    private String invoiceNumber; // Invoice number
    private double totalBeforeDiscount; // Total amount before applying discount
    private double discount; // Discount applied on the invoice
    private double totalAfterDiscount; // Total amount after discount

    // Constructor with all fields specified
    public Invoice(int idInvoice, String date, String invoiceSerialNumber, String invoiceNumber, double totalBeforeDiscount, double discount, double totalAfterDiscount) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalAfterDiscount;
        this.invoiceSerialNumber = invoiceSerialNumber;
        this.invoiceNumber = invoiceNumber;
    }

    // Constructor that calculates totalAfterDiscount automatically
    public Invoice(int idInvoice, String date, String invoiceSerialNumber, String invoiceNumber, double totalBeforeDiscount, double discount) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalBeforeDiscount - discount;
        this.invoiceSerialNumber = invoiceSerialNumber;
        this.invoiceNumber = invoiceNumber;
    }

    // Getter for invoice ID
    public int getIdInvoice() {
        return idInvoice;
    }

    // Getter for invoice date
    public String getDate() {
        return date;
    }

    // Getter for invoice serial number
    public String getInvoiceSerialNumber() {
        return invoiceSerialNumber;
    }

    // Getter for invoice number
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    // Getter for total before discount
    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    // Getter for discount
    public double getDiscount() {
        return discount;
    }

    // Getter for total after discount
    public double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    // Setter for invoice ID
    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }
}
