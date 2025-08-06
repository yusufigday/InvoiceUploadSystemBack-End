package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.service.InvoiceService;
import org.InvoiceUpload.service.ItemService;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AddInvoiceController implements HttpHandler {

    private InvoiceService invoiceService = new InvoiceService();
    private ItemService itemService = new ItemService();
    private InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);
        JSONObject invoiceJson = json.getJSONObject("invoice");

        int invoiceId = invoiceJson.getInt("invoice_id");
        String date = invoiceJson.getString("date");
        double totalBeforeDiscount = invoiceJson.getDouble("totalBeforeDiscount");
        int discount = invoiceJson.getInt("discount");
        double totalAfterDiscount = invoiceJson.getDouble("totalAfterDiscount");

        Invoice invoice = new Invoice(invoiceId, date, totalBeforeDiscount, discount, totalAfterDiscount);
        boolean invoiceAdded = invoiceService.addInvoice(invoice);

        JSONArray itemsArray = json.getJSONArray("items");
        boolean allItemsAdded = true;
        boolean allInvoiceItemsAdded = true;

        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject itemJson = itemsArray.getJSONObject(i);

            int itemId = itemJson.getInt("items_id");
            String name = itemJson.getString("name");
            int price = itemJson.getInt("price");
            int quantity = itemJson.getInt("quantity");
            int totalPrice = itemJson.getInt("totalPrice");

            Item item = new Item(itemId, name, price);
            if (!itemService.addItem(item)) {
                allItemsAdded = false;
            }

            InvoiceItems invoiceItem = new InvoiceItems(
                    0,
                    invoiceId,
                    itemId,
                    quantity,
                    price,
                    totalPrice
            );
            if (!invoiceItemsService.addInvoiceItem(invoiceItem)) {
                allInvoiceItemsAdded = false;
            }
        }

        String response;
        int statusCode;
        if (invoiceAdded && allItemsAdded && allInvoiceItemsAdded) {
            response = "Invoice, items and invoiceItems added successfully";
            statusCode = 200;
        } else {
            response = "Some data could not be added. See logs for details.\n";
            statusCode = 500;
        }

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
