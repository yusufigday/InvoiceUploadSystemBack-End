package org.InvoiceUpload.model;

public class Item {
    private int idItems;
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Item(int idItems, String name, int price) {
        this.idItems = idItems;
        this.name = name;
        this.price = price;
    }

    public int getIdItems() {
        return idItems;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}