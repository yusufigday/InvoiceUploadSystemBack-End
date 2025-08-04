package org.InvoiceUpload.model;

public class Product {
    private int idProduct;
    private String adi;
    private double fiyat;
    private int miktar;
    private double tutar;

    public Product(int idProduct, String adi, double fiyat,int miktar, double tutar){
        this.idProduct = idProduct;
        this.adi = adi;
        this.fiyat = fiyat;
        this.miktar = miktar;
        this.tutar = tutar;
    }

    public int getIdProduct(){
        return idProduct;
    }

    public String getAdi(){
        return adi;
    }

    public double getFiyat(){
        return fiyat;
    }

    public int getMiktar(){
        return miktar;
    }

    public double getTutar(){
        return tutar;
    }
}
