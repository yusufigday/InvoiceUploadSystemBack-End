package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.InvoiceItems;

import java.util.List;

// Repository interface for managing invoice items
public interface InvoiceItemsRepository {

    // Inserts a new invoice item into the database
    int insert(InvoiceItems invoiceItems) throws Exception;

    // Retrieves all invoice items from the database
    List<InvoiceItems> getAllInvoiceItems();
}
