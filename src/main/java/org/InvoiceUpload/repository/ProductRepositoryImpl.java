package org.InvoiceUpload.repository;

import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int insert(Product product) throws Exception {
        String sql = "INSERT INTO products(id_product, Adı, Fiyatı, Miktarı,Tutar) VALUES(" +
        product.getIdProduct() + ", '" +
        product.getAdi() + "', " +
        product.getFiyat() + ", " +
        product.getMiktar() + ", " +
        product.getTutar() + ")";
        return SQLManager.executeUpdate(sql);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = SQLManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("adi"),
                        rs.getDouble("fiyati"),
                        rs.getInt("miktari"),
                        rs.getDouble("tutar")
                );
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
