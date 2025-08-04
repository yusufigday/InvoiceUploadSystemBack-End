package org.InvoiceUpload.model;

public class Item {
    private int idItems;
    private  String urunAdi;

    public Item(int idItems, String urunAdi) {
        this.idItems = idItems;
        this.urunAdi = urunAdi;
    }
    public int getIdItems() {
        return idItems;
    }
    public String getUrunAdi() {
        return urunAdi;
    }
}
