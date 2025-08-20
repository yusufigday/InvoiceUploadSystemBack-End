package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Implementation of ItemRepository
public class ItemRepositoryImpl implements ItemRepository {

    @Override
    public int insert(Item item) throws Exception {
        // Insert item into the database
        String sql = "INSERT INTO items(name, price) VALUES('"
                + item.getName()
                + "', " + item.getPrice() + ")";
        return SQLManager.executeUpdate(sql);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Map each row to an Item object
                Item item = new Item(
                        rs.getInt("items_id"),
                        rs.getString("name"),
                        rs.getInt("price")
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print exception if query fails
        }

        return items;
    }

    @Override
    public Item getItemById(int itemId) throws Exception {
        String sql = "SELECT * FROM items WHERE items_id = ?";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId); // Set ID parameter for query

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Map the single row to an Item object
                    return new Item(
                            rs.getInt("items_id"),
                            rs.getString("name"),
                            rs.getInt("price")
                    );
                } else {
                    return null; // Return null if no item found
                }
            }
        }
    }
}
