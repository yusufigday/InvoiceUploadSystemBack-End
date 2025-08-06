package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.InvoiceItems;

import java.util.List;

public interface InvoiceItemsRepository {
    int insert(InvoiceItems invoiceItems) throws Exception;
    List<InvoiceItems> getAllInvoiceItems();
}
