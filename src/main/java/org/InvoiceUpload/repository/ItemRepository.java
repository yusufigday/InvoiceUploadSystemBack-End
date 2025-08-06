package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    int insert(Item item) throws Exception;
    int insertWithConnection(Item item, Connection conn) throws Exception;
    Item getById(Integer id) throws Exception;
    List<Item> getAllItems();
}
