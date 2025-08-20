package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.InvoiceUpload.service.InvoiceService;
import org.InvoiceUpload.service.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

// Controller to handle adding a new invoice via HTTP POST request
public class AddInvoiceController implements HttpHandler {

    // Services for managing invoices, items, and invoice items
    private final InvoiceService invoiceService = new InvoiceService();
    private final ItemService itemService = new ItemService();
    private final InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Only allow POST requests
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        // Read request body as a string
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);
        JSONObject invoiceJson = json.getJSONObject("invoice");

        // Extract invoice details from JSON
        String invoiceSerialNumber = invoiceJson.getString("invoiceSerialNumber");
        String invoiceNumber = invoiceJson.getString("invoiceNumber");
        String date = invoiceJson.getString("date");
        double totalBeforeDiscount = invoiceJson.getDouble("totalBeforeDiscount");
        double discount = invoiceJson.getDouble("discount");

        // Create Invoice object
        Invoice invoice = new Invoice(0, date, invoiceSerialNumber, invoiceNumber, totalBeforeDiscount, discount);

        // Add invoice using service
        boolean invoiceAdded = invoiceService.addInvoice(invoice);

        String response;
        int statusCode;

        if (!invoiceAdded) {
            response = "Invoice could not be added";
            statusCode = 500;
        } else {
            int generatedInvoiceId = invoice.getIdInvoice();
            System.out.println("Generated Invoice ID: " + generatedInvoiceId);

            JSONArray invoiceItemsArray = json.getJSONArray("invoiceItems");
            boolean allInvoiceItemsAdded = true;

            // Process each invoice item
            for (int i = 0; i < invoiceItemsArray.length(); i++) {
                JSONObject itemJson = invoiceItemsArray.getJSONObject(i);

                int itemId = itemJson.getInt("item_id");
                int quantity = itemJson.getInt("quantity");

                // Get the item from the database
                Item item = itemService.getItemById(itemId);
                if (item == null) {
                    System.out.println("Item not found with ID: " + itemId);
                    allInvoiceItemsAdded = false;
                    continue;
                }

                int price = item.getPrice();
                int totalPrice = price * quantity;

                // Create InvoiceItems object
                InvoiceItems invoiceItem = new InvoiceItems(0, generatedInvoiceId, itemId, quantity, price, totalPrice);

                // Add invoice item using service
                if (!invoiceItemsService.addInvoiceItem(invoiceItem)) {
                    allInvoiceItemsAdded = false;
                }
            }

            // Prepare response based on success/failure of invoice items
            if (allInvoiceItemsAdded) {
                response = "Invoice and all invoiceItems added successfully";
                statusCode = 200;
            } else {
                response = "Invoice added but some invoiceItems failed";
                statusCode = 500;
            }
        }

        // Send HTTP response
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
