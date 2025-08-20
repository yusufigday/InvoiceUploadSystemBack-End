package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementation of InvoiceRepository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Override
    public int insert(Invoice invoice) throws Exception {
        String sql = "INSERT INTO invoice(date, invoiceSerialNumber, invoiceNumber, totalBeforeDiscount, discount, totalAfterDiscount) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement
            ps.setString(1, invoice.getDate());
            ps.setString(2, invoice.getInvoiceSerialNumber());
            ps.setString(3, invoice.getInvoiceNumber());
            ps.setDouble(4, invoice.getTotalBeforeDiscount());
            ps.setDouble(5, invoice.getDiscount());
            ps.setDouble(6, invoice.getTotalAfterDiscount());

            int rowsAffected = ps.executeUpdate();

            // Retrieve generated AUTO_INCREMENT ID and set it to the invoice object
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        invoice.setIdInvoice(generatedId); // Critical: assign DB-generated ID to object
                        return generatedId;
                    }
                }
            }
            return 0;
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
                // Map each row to an Invoice object
                Invoice invoice = new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getString("date"),
                        rs.getString("invoiceSerialNumber"),
                        rs.getString("invoiceNumber"),
                        rs.getDouble("totalBeforeDiscount"),
                        rs.getDouble("discount"),
                        rs.getDouble("totalAfterDiscount")
                );
                invoices.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print exception on failure
        }
        return invoices;
    }

    @Override
    public int deleteById(int invoiceId) throws Exception {
        String sql = "DELETE FROM invoice WHERE invoice_id = ?";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId); // Set invoice ID for deletion
            return ps.executeUpdate();
        }
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) throws Exception {
        String sql = "SELECT * FROM invoice WHERE invoice_id = ?";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Map result set to Invoice object
                    return new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getString("date"),
                            rs.getString("invoiceSerialNumber"),
                            rs.getString("invoiceNumber"),
                            rs.getDouble("totalBeforeDiscount"),
                            rs.getDouble("discount"),
                            rs.getDouble("totalAfterDiscount")
                    );
                }
            }
        }
        return null; // Return null if invoice not found
    }
}
