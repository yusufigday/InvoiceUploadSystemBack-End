package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.InvoiceItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemsRepositoryImpl implements InvoiceItemsRepository {
    @Override
    public int insert(InvoiceItems invoiceItems) throws Exception {
        String sql = "INSERT INTO invoiceitems(invoiceitems_id, invoice_id, itemId, productQuantity, price, totalPrice) VALUES(" +
                invoiceItems.getIdInvoiceItems() + ", '" +
                invoiceItems.getInvoiceId() + "', '" +
                invoiceItems.getItemId() + "', '" +
                invoiceItems.getProductQuantity() + "', '" +
                invoiceItems.getProductPrice() + "', '" +
                invoiceItems.getTotalPrice() + "')";
        return SQLManager.executeUpdate(sql);
    }

    public int insertWithConnection(Connection conn, InvoiceItems invoiceItems) throws Exception {
        String sql = "INSERT INTO invoiceitems(invoiceitems_id, invoince_id, itemId, productQuantity, price, totalPrice) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceItems.getIdInvoiceItems());
            ps.setInt(2, invoiceItems.getInvoiceId());
            ps.setInt(3, invoiceItems.getItemId());
            ps.setInt(4, invoiceItems.getProductQuantity());
            ps.setInt(5, invoiceItems.getProductPrice());
            ps.setInt(6, invoiceItems.getTotalPrice());
            return ps.executeUpdate();
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
                        rs.getInt("invoince_id"),
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

    @Override
    public int insertWithConnection(InvoiceItems invoiceItems, Connection conn) {
        return 0;
    }
}
