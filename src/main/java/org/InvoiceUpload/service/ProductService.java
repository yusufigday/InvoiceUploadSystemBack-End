package org.InvoiceUpload.service;

import org.InvoiceUpload.model.Product;
import org.InvoiceUpload.repository.ProductRepository;
import org.InvoiceUpload.repository.ProductRepositoryImpl;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository = new ProductRepositoryImpl();

    public boolean addProduct(Product product) {
        try {
            return productRepository.insert(product) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

}
