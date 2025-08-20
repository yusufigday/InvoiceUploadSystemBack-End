package org.InvoiceUpload.service;

import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.repository.InvoiceItemsRepository;
import org.InvoiceUpload.repository.InvoiceItemsRepositoryImpl;

import java.util.List;

// Service layer for managing invoice items
public class InvoiceItemsService {
    private final InvoiceItemsRepository invoiceItemsRepository = new InvoiceItemsRepositoryImpl();

    // Adds a new invoice item using the repository
    public boolean addInvoiceItem(InvoiceItems invoiceItems) {
        try {
            return invoiceItemsRepository.insert(invoiceItems) > 0; // Returns true if insert succeeded
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception
            return false;
        }
    }

    // Retrieves all invoice items
    public List<InvoiceItems> getAllInvoiceItems() {
        return invoiceItemsRepository.getAllInvoiceItems();
    }
}
