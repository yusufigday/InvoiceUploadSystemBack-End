package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Invoice;

import java.util.List;

public interface InvoiceRepository {
    int insert(Invoice invoice) throws Exception;
    List<Invoice> getAllInvoice();

}
