package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.service.InvoiceService;
import org.InvoiceUpload.service.ItemService;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AddInvoiceController implements HttpHandler {
    private final InvoiceService invoiceService = new InvoiceService();
    private final ItemService itemService = new ItemService();
    private final InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);
        JSONObject invoiceJson = json.getJSONObject("invoice");

        String invoiceSerialNumber = invoiceJson.getString("invoiceSerialNumber");
        String invoiceNumber = invoiceJson.getString("invoiceNumber");
        String date = invoiceJson.getString("date");
        double totalBeforeDiscount = invoiceJson.getDouble("totalBeforeDiscount");
        double discount = invoiceJson.getDouble("discount");

        Invoice invoice = new Invoice(0, date, invoiceSerialNumber, invoiceNumber, totalBeforeDiscount, discount);

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

            for (int i = 0; i < invoiceItemsArray.length(); i++) {
                JSONObject itemJson = invoiceItemsArray.getJSONObject(i);

                int itemId = itemJson.getInt("item_id");
                int quantity = itemJson.getInt("quantity");

                Item item = itemService.getItemById(itemId);
                if (item == null) {
                    System.out.println("Item not found with ID: " + itemId);
                    allInvoiceItemsAdded = false;
                    continue;
                }

                int price = item.getPrice();
                int totalPrice = price * quantity;

                InvoiceItems invoiceItem = new InvoiceItems(0,
                        generatedInvoiceId,
                        itemId, quantity, price, totalPrice);

                if (!invoiceItemsService.addInvoiceItem(invoiceItem)) {
                    allInvoiceItemsAdded = false;
                }
            }

            if (allInvoiceItemsAdded) {
                response = "Invoice and all invoiceItems added successfully";
                statusCode = 200;
            } else {
                response = "Invoice added but some invoiceItems failed";
                statusCode = 500;
            }
        }

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
