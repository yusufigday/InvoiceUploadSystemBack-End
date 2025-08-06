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
        String sql = "INSERT INTO invoiceitems(invoiceitems_id, invoiceId, itemId, productQuantity, price, totalPrice) VALUES(" +
                invoiceItems.getIdInvoiceItems() + ", '" +
                invoiceItems.getInvoiceId() + "', '" +
                invoiceItems.getItemId() + "', '" +
                invoiceItems.getProductQuantity() + "', '"  +
                invoiceItems.getProductPrice() + "', '" +
                invoiceItems.getTotalPrice() + "')";
        return SQLManager.executeUpdate(sql);
    }

    @Override
    public List<InvoiceItems> getAllInvoiceItems() {
        List<InvoiceItems> invoiceitems = new ArrayList<>();
        String sql = "SELECT * FROM invoiceitems";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                InvoiceItems invoiceItems = new InvoiceItems(
                        rs.getInt("idInvoiceItems"),
                        rs.getInt("invoiceId"),
                        rs.getInt("itemId"),
                        rs.getInt("productQuantity"),
                        rs.getInt("price"),
                        rs.getInt("totalPrice")
                        );
                invoiceitems.add(invoiceItems);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return invoiceitems;
    }

}

