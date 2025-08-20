package org.InvoiceUpload.model;

// Model class representing an item in an invoice
public class InvoiceItems {
    private int idInvoiceItems; // Unique ID for the invoice item
    private int invoiceId;      // Reference to the parent invoice
    private int itemId;         // Reference to the product/item
    private int productQuantity;
    private int productPrice;
    private int totalPrice;     // Calculated as productPrice * productQuantity

    public InvoiceItems(int idInvoiceItems, int invoiceId, int itemId, int productQuantity, int productPrice, int totalPrice) {
        this.idInvoiceItems = idInvoiceItems;
        this.invoiceId = invoiceId;
        this.itemId = itemId;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
    }

    public int getIdInvoiceItems() {
        return idInvoiceItems;
    }

    public int getInvoiceId() {
        return invoiceId;
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

    public void setIdInvoiceItems(int idInvoiceItems) {
        this.idInvoiceItems = idInvoiceItems;
    }
}
