package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

// Controller to handle HTTP requests for invoice items
public class InvoiceItemsController implements HttpHandler {

    // Service to manage invoice items
    private InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        // Handle POST and GET requests
        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    // Handle POST request: add a new invoice item
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);

        // Extract fields from JSON
        int invoiceItemsId = json.getInt("invoiceitems_id");
        int invoiceId = json.getInt("invoice_id");
        int itemId = json.getInt("itemId");
        int productQuantity = json.getInt("productQuantity");
        int price = json.getInt("price");
        int totalPrice = json.getInt("totalPrice");

        // Create InvoiceItems object
        InvoiceItems invoiceItems = new InvoiceItems(invoiceItemsId, invoiceId, itemId, productQuantity, price, totalPrice);

        // Add invoice item using service
        boolean success = invoiceItemsService.addInvoiceItem(invoiceItems);

        String response = success ? "InvoiceItems added." : "An error occurred while adding an invoice item";
        byte[] responseBytes = response.getBytes("UTF-8");

        // Send HTTP response
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    // Handle GET request: retrieve all invoice items
    private void handleGet(HttpExchange exchange) throws IOException {
        var invoiceItems = invoiceItemsService.getAllInvoiceItems();

        JSONArray jsonArray = new JSONArray();

        // Convert each invoice item to JSON
        for (InvoiceItems invoiceItem : invoiceItems) {
            JSONObject json = new JSONObject();
            json.put("invoiceitems_id", invoiceItem.getIdInvoiceItems());
            json.put("invoice_id", invoiceItem.getInvoiceId());
            json.put("itemId", invoiceItem.getItemId());
            json.put("productQuantity", invoiceItem.getProductQuantity());
            json.put("price", invoiceItem.getProductPrice());
            json.put("totalPrice", invoiceItem.getTotalPrice()); // Fixed: use correct totalPrice

            jsonArray.put(json);
        }

        // Send JSON response
        byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
