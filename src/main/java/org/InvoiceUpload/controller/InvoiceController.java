package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.db.SQLManager;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.service.InvoiceService;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;

public class InvoiceController implements HttpHandler {
    private InvoiceService invoiceService = new InvoiceService();
    private InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);

        int idInvoice = json.getInt("invoice_id");
        String date = json.getString("date");
        double totalBeforeDiscount = json.getDouble("totalBeforeDiscount");
        int discount = json.getInt("discount");
        double totalAfterDiscount = json.getDouble("totalAfterDiscount");

        Invoice invoice = new Invoice(idInvoice, date, totalBeforeDiscount, discount, totalAfterDiscount);
        JSONArray itemsArray = json.getJSONArray("items");
        // items JSON dizisi bekleniyor:
        // örnek:
        // "items": [
        //   { "invoiceitems_id":1, "itemId":2, "productQuantity":3, "price":50, "totalPrice":150 },
        //   ...
        // ]

        boolean success = false;

        try (Connection conn = SQLManager.getConnection()) {
            conn.setAutoCommit(false); // Transaction başlat

            // 1. invoice ekle
            if (invoiceService.addInvoiceWithConnection(conn, invoice)) {
                // 2. invoice items ekle
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemJson = itemsArray.getJSONObject(i);
                    InvoiceItems invoiceItem = new InvoiceItems(
                            itemJson.getInt("invoiceitems_id"),
                            idInvoice,
                            itemJson.getInt("itemId"),
                            itemJson.getInt("productQuantity"),
                            itemJson.getInt("price"),
                            itemJson.getInt("totalPrice")
                    );
                    boolean itemAdded = invoiceItemsService.addInvoiceItemWithConnection(conn, invoiceItem);
                    if (!itemAdded) {
                        throw new Exception("InvoiceItem eklenemedi!");
                    }
                }
                conn.commit();
                success = true;
            } else {
                throw new Exception("Invoice eklenemedi!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        String response = success ? "Invoice and InvoiceItems added." : "An error occurred while registering.";
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        var invoice = invoiceService.getAllInvoice();

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for (Invoice i : invoice) {
            JSONObject json = new JSONObject();
            json.put("invoice_id", i.getIdInvoice());
            json.put("date", i.getDate());
            json.put("totalBeforeDiscount", i.getTotalBeforeDiscount());
            json.put("discount", i.getDiscount());
            json.put("totalAfterDiscount", i.getTotalAfterDiscount());
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
