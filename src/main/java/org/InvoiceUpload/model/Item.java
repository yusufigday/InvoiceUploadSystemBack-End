package org.InvoiceUpload.model;

// Model class representing a product/item
public class Item {
    private int idItems; // Unique ID of the item in the database
    private String name;
    private int price;

    // Constructor for creating a new item (ID not assigned yet)
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    // Constructor for an existing item (ID already assigned)
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
