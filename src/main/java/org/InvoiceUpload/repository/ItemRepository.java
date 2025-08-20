package org.InvoiceUpload.repository;


import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    int insert(Item item) throws Exception;

    List<Item> getAllItems();

    Item getItemById(int itemId) throws Exception;
}
