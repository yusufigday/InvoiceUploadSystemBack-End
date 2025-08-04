package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public int insert(Item item) throws Exception {
        String sql = "INSERT INTO items(id_items, urun_adi) VALUES("
                + item.getIdItems()
                + ", '"+item.getUrunAdi()+"')";
        return SQLManager.executeUpdate(sql);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                Item item = new Item(
                        rs.getInt("id_items"),
                        rs.getString("urun_adi")
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
