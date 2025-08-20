package org.InvoiceUpload.model;

public class InvoiceItems {
    private int idInvoiceItems;
    private int invoiceId;
    private int itemId;
    private int productQuantity;
    private int productPrice;
    private int totalPrice;

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
