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

import javax.swing.event.HyperlinkListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AddInvoiceController implements HttpHandler {
    private InvoiceService invoiceService = new InvoiceService();
    private ItemService itemService = new ItemService();
    private InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405,-1);
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
        double discount = invoiceJson.getDouble("discount");

        Invoice invoice = new Invoice(invoiceId, date, totalBeforeDiscount, discount);

        boolean invoiceAdded = invoiceService.addInvoice(invoice);

        JSONArray invoiceItemsArray = json.getJSONArray("invoiceItems");
        boolean allInvoiceItemsAdded = true;

        for (int i = 0; i < invoiceItemsArray.length(); i++){
            JSONObject itemJson = invoiceItemsArray.getJSONObject(i);

            int itemId = itemJson.getInt("item_id");
            int quantity = itemJson.getInt("quantity");

            Item item = itemService.getItemById(itemId);

            if (item == null){
                System.out.println("Item not found with ID: " + itemId);
                allInvoiceItemsAdded = false;
                continue;
            }

            int price = item.getPrice();
            int totalPrice = price * quantity;

            InvoiceItems invoiceItem = new InvoiceItems(
                    0,
                    invoiceId,
                    itemId,
                    quantity,
                    price,
                    totalPrice
            );

            if (!invoiceItemsService.addInvoiceItem(invoiceItem)){
                allInvoiceItemsAdded = false;
            }
        }

        String response;
        int statusCode;

        if (invoiceAdded && allInvoiceItemsAdded) {
            response = "Invoice and invoiceItems added successfully using items table data";
            statusCode = 200;
        }else{
            response = "Some invoiceItems could net be added. Check logs.";
            statusCode = 500;
        }

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();


    }
}