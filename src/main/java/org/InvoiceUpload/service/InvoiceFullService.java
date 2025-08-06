package org.InvoiceUpload.service;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.repository.InvoiceItemsRepository;
import org.InvoiceUpload.repository.InvoiceItemsRepositoryImpl;
import org.InvoiceUpload.repository.InvoiceRepository;
import org.InvoiceUpload.repository.InvoiceRepositoryImpl;
import org.InvoiceUpload.repository.ItemRepository;
import org.InvoiceUpload.repository.ItemRepositoryImpl;

import java.sql.Connection;
import java.util.List;

public class InvoiceFullService {
    private InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
    private InvoiceItemsRepository invoiceItemsRepository = new InvoiceItemsRepositoryImpl();
    private ItemRepository itemRepository = new ItemRepositoryImpl();

    public boolean addInvoiceWithItems(Invoice invoice, List<InvoiceItems> invoiceItemsList, List<Item> items) {
        Connection conn = null;
        try {
            conn = SQLManager.getConnection();
            conn.setAutoCommit(false);

            int resultInvoice = invoiceRepository.insertWithConnection(conn, invoice);
            if (resultInvoice <= 0) {
                conn.rollback();
                return false;
            }

            for (Item item : items) {
                Item existingItem = itemRepository.getById(item.getIdItems());
                if (existingItem == null) {
                    int resultItem = itemRepository.insertWithConnection(item, conn);
                    if (resultItem <= 0) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            for (InvoiceItems invoiceItems : invoiceItemsList) {
                int resultInvoiceItem = invoiceItemsRepository.insertWithConnection(invoiceItems, conn);
                if (resultInvoiceItem <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }
}
