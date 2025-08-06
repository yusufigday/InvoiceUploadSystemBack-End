package org.InvoiceUpload.model;

public class InvoiceItems {
    private int idInvoiceItems;
    private int invoiceId;
    private int itemId;
    private int productQuantity;
    private int productPrice;
    private int totalPrice;

    public InvoiceItems(int idInvoiceItems, int itemId, int productQuantity, int productPrice, int totalPrice, int price) {
        this.idInvoiceItems = idInvoiceItems;
        this.itemId = itemId;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
    }

    public int getIdInvoiceItems() {
        return idInvoiceItems;
    }

    public int getItemId() {
        return itemId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getInvoiceId() {
        return invoiceId;
    }
}
