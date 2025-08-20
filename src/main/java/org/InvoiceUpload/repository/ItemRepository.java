package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Item;

import java.util.List;

// Repository interface for managing items
public interface ItemRepository {

    // Inserts a new item into the database
    int insert(Item item) throws Exception;

    // Retrieves all items from the database
    List<Item> getAllItems();

    // Retrieves a single item by its ID
    Item getItemById(int itemId) throws Exception;
}
