package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Item;

import java.util.List;

public interface ItemRepository {
    int insert(Item item) throws Exception;
    List<Item> getAllItems();
}
