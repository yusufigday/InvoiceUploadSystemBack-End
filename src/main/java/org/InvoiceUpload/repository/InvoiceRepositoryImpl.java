package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {


    @Override
    public int insert(Invoice invoice) throws Exception {
        String sql = "INSERT INTO invoice(date, invoiceSerialNumber, invoiceNumber, totalBeforeDiscount, discount, totalAfterDiscount) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, invoice.getDate());
            ps.setString(2, invoice.getInvoiceSerialNumber());
            ps.setString(3, invoice.getInvoiceNumber());
            ps.setDouble(4, invoice.getTotalBeforeDiscount());
            ps.setDouble(5, invoice.getDiscount());
            ps.setDouble(6, invoice.getTotalAfterDiscount());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        invoice.setIdInvoice(generatedId); // Invoice nesnesine set et
                        return generatedId;
                    }
                }
            }
            return 0;
        }
    }


    @Override
    public int insertWithConnection(Connection conn, Invoice invoice) throws Exception {
        String sql = "INSERT INTO invoice(date, invoiceSerialNumber, invoiceNumber, totalBeforeDiscount, discount, totalAfterDiscount) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, invoice.getDate());
            ps.setString(2, invoice.getInvoiceSerialNumber());
            ps.setString(3, invoice.getInvoiceNumber());
            ps.setDouble(4, invoice.getTotalBeforeDiscount());
            ps.setDouble(5, invoice.getDiscount());
            ps.setDouble(6, invoice.getTotalAfterDiscount());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        invoice.setIdInvoice(generatedId);
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
            e.printStackTrace();
        }
        return invoices;
    }


    @Override
    public int deleteById(int invoiceId) throws Exception {
        String sql = "DELETE FROM invoice WHERE invoice_id = ?";
        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
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
        return null;
    }

}