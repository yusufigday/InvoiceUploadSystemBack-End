package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.repository.InvoiceRepository;
import org.InvoiceUpload.repository.InvoiceRepositoryImpl;

import java.util.List;

// Service layer for invoice-related operations
public class InvoiceService {
    private final InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();

    // Adds a new invoice and returns true if insertion succeeded
    public boolean addInvoice(Invoice invoice) {
        try {
            return invoiceRepository.insert(invoice) > 0; // Uses repository to insert invoice
        } catch (Exception e) {
            e.printStackTrace(); // Log exceptions
            return false;
        }
    }

    // Retrieves all invoices
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.getAllInvoice();
    }

    // Deletes an invoice by ID and returns true if deletion succeeded
    public boolean deleteInvoiceById(int invoiceId) {
        try {
            return ((InvoiceRepositoryImpl) invoiceRepository).deleteById(invoiceId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieves a single invoice by ID
    public Invoice getInvoiceById(int invoiceId) {
        try {
            return ((InvoiceRepositoryImpl) invoiceRepository).getInvoiceById(invoiceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
