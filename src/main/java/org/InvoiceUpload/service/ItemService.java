package org.InvoiceUpload.service;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.repository.ItemRepository;
import org.InvoiceUpload.repository.ItemRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private ItemRepository itemRepository = new ItemRepositoryImpl();

    public boolean addItem(Item item){
        try {
            return itemRepository.insert(item) > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getAllItems(){
            return itemRepository.getAllItems();

    }

}
