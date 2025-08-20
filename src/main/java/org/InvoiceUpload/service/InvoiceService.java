package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.repository.InvoiceRepository;
import org.InvoiceUpload.repository.InvoiceRepositoryImpl;

import java.util.List;

public class InvoiceService {
    private final InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();

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

    public boolean deleteInvoiceById(int invoiceId) {
        try {
            return ((InvoiceRepositoryImpl) invoiceRepository).deleteById(invoiceId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Invoice getInvoiceById(int invoiceId) {
        try {
            return ((InvoiceRepositoryImpl) invoiceRepository).getInvoiceById(invoiceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
