package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Product;
import org.InvoiceUpload.service.ProductService;
import org.json.JSONObject;

import java.io.*;

public class ProductController implements HttpHandler {
    private ProductService productService = new ProductService();


        @Override
        public void handle (HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            if (method.equalsIgnoreCase("POST")) {
                handlePost(exchange);
            } else if (method.equalsIgnoreCase("GET")) {
                handleGet(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }

        private void handlePost (HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is))
                    .lines().reduce("", (acc, line) -> acc + line);

            JSONObject json = new JSONObject(body);

            int idProduct = json.getInt("id_product");
            String adi = json.getString("Adı");
            double fiyat = json.getDouble("Fiyatı");
            int miktar = json.getInt("Miktarı");
            double tutar = json.getDouble("Tutar");

            Product product = new Product(idProduct, adi, fiyat, miktar, tutar);
            boolean success = productService.addProduct(product);

            String response = success ? "Product eklendi." : "Product eklenirken hata oluştu.";
            byte[] responseBytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private void handleGet (HttpExchange exchange) throws IOException {
            var products = productService.getAllProducts();

            org.json.JSONArray jsonArray = new org.json.JSONArray();
            for (Product p : products) {
                JSONObject json = new JSONObject();
                json.put("id_product", p.getIdProduct());
                json.put("adi", p.getAdi());
                json.put("fiyati", p.getFiyat());
                json.put("miktari", p.getMiktar());
                json.put("tutar", p.getTutar());
                jsonArray.put(json);
            }

            byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

    }

