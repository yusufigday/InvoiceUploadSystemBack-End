package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    public int insertWithConnection(Item item, Connection conn) throws Exception {
        String sql = "INSERT INTO items(items_id, name, price) VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getIdItems());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getPrice());
            return ps.executeUpdate();
        }
    }

    public Item getById(Integer id) throws Exception {
        String sql = "SELECT * FROM items WHERE items_id = ?";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(rs.getInt("items_id"), rs.getString("name"), rs.getInt("price"));
            }
            return null;
        }
    }

    @Override
    public int insert(Item item) throws Exception {
        String sql = "INSERT INTO items(items_id, name, price) VALUES("
                + item.getIdItems()
                + ", '"+item.getName()
                + "', "+item.getPrice()+")";
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
                        rs.getInt("items_id"),
                        rs.getString("name"),
                        rs.getInt("price")
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return items;
    }

    @Override
    public Item getItemById(int itemId) throws Exception {
        String sql = "SELECT * FROM items WHERE items_id = ?";
        try(Connection conn = SQLManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Item(rs.getInt("items_id"),
                            rs.getString("name"),
                            rs.getInt("price"));
                }else {
                    return null;
                }
            }
        }
    }
}
