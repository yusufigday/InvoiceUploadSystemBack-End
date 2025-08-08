package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.service.InvoiceService;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class DeleteInvoiceController implements HttpHandler {

    private InvoiceService invoiceService = new InvoiceService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        // Request body oku
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();

        String response;
        int statusCode;

        try {
            JSONObject json = new JSONObject(body);
            int invoiceId = json.getInt("invoice_id");

            boolean success = invoiceService.deleteInvoiceById(invoiceId);

            if (success) {
                response = "Invoice (and related invoiceitems) deleted successfully.";
                statusCode = 200;
            } else {
                response = "Failed to delete invoice.";
                statusCode = 500;
            }

        } catch (Exception e) {
            response = "Invalid request body or missing invoice_id.";
            statusCode = 400;
        }

        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
