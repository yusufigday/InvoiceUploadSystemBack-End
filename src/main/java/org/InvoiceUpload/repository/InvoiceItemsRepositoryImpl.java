package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.InvoiceItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemsRepositoryImpl implements InvoiceItemsRepository {

    @Override
    public int insert(InvoiceItems invoiceItems) throws Exception {
        String sql = "INSERT INTO invoiceitems (invoice_id, itemId, productQuantity, price, totalPrice) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, invoiceItems.getInvoiceId());
            ps.setInt(2, invoiceItems.getItemId());
            ps.setInt(3, invoiceItems.getProductQuantity());
            ps.setInt(4, invoiceItems.getProductPrice());
            ps.setInt(5, invoiceItems.getTotalPrice());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        invoiceItems.setIdInvoiceItems(rs.getInt(1)); // AUTO_INCREMENT ID set ediliyor
                    }
                }
            }

            return affected;
        }
    }

    public int insertWithConnection(Connection conn, InvoiceItems invoiceItems) throws Exception {
        String sql = "INSERT INTO invoiceitems (invoice_id, itemId, productQuantity, price, totalPrice) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, invoiceItems.getInvoiceId());
            ps.setInt(2, invoiceItems.getItemId());
            ps.setInt(3, invoiceItems.getProductQuantity());
            ps.setInt(4, invoiceItems.getProductPrice());
            ps.setInt(5, invoiceItems.getTotalPrice());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        invoiceItems.setIdInvoiceItems(rs.getInt(1));
                    }
                }
            }

            return affected;
        }
    }

    @Override
    public List<InvoiceItems> getAllInvoiceItems() {
        List<InvoiceItems> invoiceitems = new ArrayList<>();
        String sql = "SELECT * FROM invoiceitems";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InvoiceItems invoiceItems = new InvoiceItems(
                        rs.getInt("invoiceitems_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("itemId"),
                        rs.getInt("productQuantity"),
                        rs.getInt("price"),
                        rs.getInt("totalPrice")
                );
                invoiceitems.add(invoiceItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceitems;
    }

}
