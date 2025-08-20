package org.InvoiceUpload.model;

public class Customer {
    private String adi;
    private String soyadi;
    private String tckn;

    public Customer(String adi, String soyadi, String tckn) {
        this.adi = adi;
        this.soyadi = soyadi;
        this.tckn = tckn;
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
