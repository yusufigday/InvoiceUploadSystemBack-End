package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.service.InvoiceItemsService;
import org.InvoiceUpload.service.InvoiceService;
import org.json.JSONObject;

import java.io.*;

public class InvoiceItemsController implements HttpHandler {
    private InvoiceItemsService invoiceItemsService = new InvoiceItemsService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if(method.equalsIgnoreCase("POST")){
            handlePost(exchange);
        }else if (method.equalsIgnoreCase("GET")){
            handleGet(exchange);
        }else{
            exchange.sendResponseHeaders(405,-1);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);


        int invoiceItemsId = json.getInt("invoiceitems_id");
        int invoiceId = json.getInt("invoice_id");
        int itemId = json.getInt("itemId");
        int productQuantity = json.getInt("productQuantity");
        int price = json.getInt("price");
        int totalPrice = json.getInt("totalPrice");

        InvoiceItems invoiceItems = new InvoiceItems(invoiceItemsId,invoiceId , itemId, productQuantity, price, totalPrice);
        boolean success = invoiceItemsService.addInvoiceItem(invoiceItems);

        String response = success ? "InvoiceItems added." : "An error occured while adding an invoice items";
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }


    private void handleGet(HttpExchange exchange) throws IOException {
        var invoiceItems = invoiceItemsService.getAllInvoiceItems();

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for (InvoiceItems invoiceItem : invoiceItems) {
            JSONObject json = new JSONObject();
            json.put("invoiceitems_id", invoiceItem.getIdInvoiceItems());
            json.put("invoice_id", invoiceItem.getInvoiceId());
            json.put("itemId", invoiceItem.getItemId());
            json.put("productQuantity", invoiceItem.getProductQuantity());
            json.put("price", invoiceItem.getProductPrice());
            json.put("totalPrice", invoiceItem.getProductPrice());

        }

        byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

}


