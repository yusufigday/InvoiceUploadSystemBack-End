package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.service.InvoiceService;

import java.io.IOException;
import java.io.OutputStream;

// Controller to handle deletion of an invoice via HTTP DELETE request
public class DeleteInvoiceController implements HttpHandler {

    // Service to manage invoices
    private InvoiceService invoiceService = new InvoiceService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Only allow DELETE requests
        if (!exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        // Extract invoice ID from URL path
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        String response;
        int statusCode;

        try {
            if (parts.length < 3) {
                throw new IllegalArgumentException("Missing ID parameter.");
            }

            int invoiceId = Integer.parseInt(parts[2]);

            // Attempt to delete the invoice by ID
            boolean success = invoiceService.deleteInvoiceById(invoiceId);

            if (success) {
                response = "Invoice (and related invoice items) deleted successfully.";
                statusCode = 200;
            } else {
                response = "Failed to delete invoice.";
                statusCode = 500;
            }

        } catch (Exception e) {
            response = "Invalid or missing invoice ID.";
            statusCode = 400;
        }

        // Send HTTP response
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
