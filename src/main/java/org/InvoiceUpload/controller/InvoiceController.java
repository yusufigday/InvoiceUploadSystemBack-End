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
        } else if(method.equalsIgnoreCase("DELETE")) {
            handleDelete(exchange);
        }else {
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
        double discount = json.getDouble("discount");

        Invoice invoice = new Invoice(idInvoice, date, totalBeforeDiscount, discount);

        org.json.JSONArray itemsArray = json.getJSONArray("items");

        boolean success = false;

        try (Connection conn = SQLManager.getConnection()) {
            conn.setAutoCommit(false);

            if (invoiceService.addInvoiceWithConnection(conn, invoice)) {
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

    private void handleDelete(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        int invoiceId = -1;

        if (query != null){
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("invoice_id")) {
                    try {
                        invoiceId = Integer.parseInt(keyValue[1]);
                    }catch (NumberFormatException e){
                        invoiceId = -1;
                    }
                }
            }
        }
        String response;
        int statusCode;
        if (invoiceId == -1) {
            response = "Missing or invalid invoice_id parameter.";
            statusCode = 400;
        }else {
            boolean success = new InvoiceService().deleteInvoiceById(invoiceId);
            if (success){
                response = "Invoice (and related invoiceitems) deleted successfully.";
                statusCode = 200;
            }else {
                response = "Failed to delete invoice.";
                statusCode = 500;
            }
        }

        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();

    }
}
