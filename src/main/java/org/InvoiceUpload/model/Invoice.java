package org.InvoiceUpload.model;

public class Invoice {
    private int idInvoice;
    private String date;
    private double totalBeforeDiscount;
    private double discount;
    private double totalAfterDiscount;

    public Invoice(int idInvoice, String date, double totalBeforeDiscount, double discount) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalBeforeDiscount - discount;
    }

    public int getIdInvoice(){
        return idInvoice;
    }

    public String getDate(){
        return date;
    }

    public double getTotalBeforeDiscount(){
        return totalBeforeDiscount;
    }

    public double getDiscount(){
        return discount;
    }

    public double getTotalAfterDiscount(){
        return totalAfterDiscount;
    }
}
