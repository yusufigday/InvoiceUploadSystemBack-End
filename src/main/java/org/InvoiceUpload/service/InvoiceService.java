package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.repository.InvoiceRepository;
import org.InvoiceUpload.repository.InvoiceRepositoryImpl;

import java.util.List;

public class InvoiceService {
    private InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();

    public boolean addInvoice(Invoice invoice) {
        try {
            return invoiceRepository.insert(invoice) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Invoice> getAllInvoice() {
        return invoiceRepository.getAllInvoice();
    }

}
