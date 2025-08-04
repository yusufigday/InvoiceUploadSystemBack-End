package org.InvoiceUpload.repository;

import org.InvoiceUpload.model.Product;

import java.util.List;

public interface ProductRepository {
    int insert(Product product) throws Exception;
    List<Product> getAllProducts();

}
