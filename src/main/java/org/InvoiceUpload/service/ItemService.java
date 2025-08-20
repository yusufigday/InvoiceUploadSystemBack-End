package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.repository.ItemRepository;
import org.InvoiceUpload.repository.ItemRepositoryImpl;

import java.util.List;

// Service layer for item-related operations
public class ItemService {
    private ItemRepository itemRepository = new ItemRepositoryImpl();

    // Adds a new item, returns true if insertion succeeded
    public boolean addItem(Item item) {
        try {
            return itemRepository.insert(item) > 0; // Repository handles insertion
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
            return false;
        }
    }

    // Retrieves a single item by its ID
    public Item getItemById(int itemId) {
        try {
            return ((ItemRepositoryImpl) itemRepository).getItemById(itemId); // Cast to implementation to access getItemById
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves all items
    public List<Item> getAllItems() {
        return itemRepository.getAllItems(); // Repository handles fetching
    }
}
