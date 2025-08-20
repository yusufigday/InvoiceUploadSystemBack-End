package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Invoice;

import java.util.List;

// Repository interface for managing invoices
public interface InvoiceRepository {

    // Inserts a new invoice into the database
    int insert(Invoice invoice) throws Exception;

    // Retrieves all invoices from the database
    List<Invoice> getAllInvoice();

    // Deletes an invoice by its ID (and potentially related invoice items)
    int deleteById(int invoiceId) throws Exception;

    // Retrieves a single invoice by its ID
    Invoice getInvoiceById(int invoiceId) throws Exception;
}
