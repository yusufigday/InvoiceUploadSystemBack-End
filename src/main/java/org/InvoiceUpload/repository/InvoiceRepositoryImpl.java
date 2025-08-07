package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Override
    public int insert(Invoice invoice) throws Exception {
        String sql = "INSERT INTO invoice(invoice_id, date, totalBeforeDiscount, discount, totalAfterDiscount) VALUES(" +
                invoice.getIdInvoice() + ", '" +
                invoice.getDate() + "', '" +
                invoice.getTotalBeforeDiscount() + "', '" +
                invoice.getDiscount() + "', " +
                invoice.getTotalAfterDiscount() + ")";
        return SQLManager.executeUpdate(sql);
    }

    public int insertWithConnection(Connection conn, Invoice invoice) throws Exception {
        String sql = "INSERT INTO invoice(invoice_id, date, totalBeforeDiscount, discount, totalAfterDiscount) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoice.getIdInvoice());
            ps.setString(2, invoice.getDate());
            ps.setDouble(3, invoice.getTotalBeforeDiscount());
            ps.setDouble(4, invoice.getDiscount());
            ps.setDouble(5, invoice.getTotalAfterDiscount());
            return ps.executeUpdate();
        }
    }

    @Override
    public List<Invoice> getAllInvoice() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoice";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getString("date"),
                        rs.getDouble("totalBeforeDiscount"),
                        rs.getDouble("discount") // int ise double olarak alabilirsiniz
                );

                invoices.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
