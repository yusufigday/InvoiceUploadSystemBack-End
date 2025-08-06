package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.InvoiceItems;

import java.sql.Connection;
import java.util.List;

public interface InvoiceItemsRepository {
    int insert(InvoiceItems invoiceItems) throws Exception;
    List<InvoiceItems> getAllInvoiceItems();

    int insertWithConnection(InvoiceItems invoiceItems, Connection conn);
}
