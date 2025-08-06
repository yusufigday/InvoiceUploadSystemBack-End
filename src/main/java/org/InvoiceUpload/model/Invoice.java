package org.InvoiceUpload.model;

public class Invoice {
    private int idInvoice;
    private String date;
    private double totalBeforeDiscount;
    private double discount;
    private double totalAfterDiscount;

    public Invoice(int idInvoice, String date, double totalBeforeDiscount, int discount, double totalAfterDiscount){
        this.idInvoice = idInvoice;
        this.date = date;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.discount = discount;
        this.totalAfterDiscount = totalAfterDiscount;
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
