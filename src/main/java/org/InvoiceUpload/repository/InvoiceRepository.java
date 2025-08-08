package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Invoice;

import java.sql.Connection;
import java.util.List;

public interface InvoiceRepository {
    int insert(Invoice invoice) throws Exception;
    int insertWithConnection(Connection conn, Invoice invoice) throws Exception;
    List<Invoice> getAllInvoice();
    int deleteById(int invoiceId) throws Exception;
}
