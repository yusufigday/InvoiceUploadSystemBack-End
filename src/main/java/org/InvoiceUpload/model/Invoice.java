package org.InvoiceUpload.model;

public class Invoice {
    private int idInvoice;
    private String date;
    private String invoiceSerialNumber;
    private String invoiceNumber;
    private double totalBeforeDiscount;
    private double discount;
    private double totalAfterDiscount;

    public Invoice(int idInvoice, String date, String invoiceSerialNumber, String invoiceNumber, double totalBeforeDiscount, double discount, double totalAfterDiscount) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalAfterDiscount;
        this.invoiceSerialNumber = invoiceSerialNumber;
        this.invoiceNumber = invoiceNumber;
    }

    public Invoice(int idInvoice, String date, String invoiceSerialNumber, String invoiceNumber, double totalBeforeDiscount, double discount) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalBeforeDiscount - discount;
        this.invoiceSerialNumber = invoiceSerialNumber;
        this.invoiceNumber = invoiceNumber;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public String getDate() {
        return date;
    }

    public String getInvoiceSerialNumber() {
        return invoiceSerialNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

}
