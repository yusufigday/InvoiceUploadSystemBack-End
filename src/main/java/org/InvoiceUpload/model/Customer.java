package org.InvoiceUpload.model;

public class Customer {
    private int idCustomer;
    private String adi;
    private String soyadi;
    private String tckn;

    public Customer(int idCustomer, String adi, String soyadi, String tckn){
        this.idCustomer = idCustomer;
        this.adi = adi;
        this.soyadi = soyadi;
        this.tckn = tckn;
    }

    public int getIdCustomer() {
        return idCustomer;
    }
    public String getAdi() {
        return adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public String getTckn() {
        return tckn;
    }

}
