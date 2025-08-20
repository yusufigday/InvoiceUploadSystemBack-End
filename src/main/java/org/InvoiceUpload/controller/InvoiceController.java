package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.service.InvoiceService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

// Controller to handle HTTP requests related to invoices
public class InvoiceController implements HttpHandler {

    // Service to manage invoices
    private InvoiceService invoiceService = new InvoiceService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        // Handle GET requests
        if (method.equalsIgnoreCase("GET")) {
            String path = exchange.getRequestURI().getPath();

            // If path contains invoice ID, fetch specific invoice
            if (path.matches(".*/invoices/\\d+$")) {
                int invoiceId = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                handleGetById(exchange, invoiceId);
            } else {
                // Otherwise, fetch all invoices
                handleGetAll(exchange);
            }
        }
    }

    // Handle GET request to retrieve all invoices
    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Invoice> invoices = invoiceService.getAllInvoice();
        JSONArray jsonInvoices = new JSONArray();
        StringBuilder xmlBuilder = new StringBuilder("<invoices>");

        // Convert each invoice to JSON and XML
        for (Invoice invoice : invoices) {
            JSONObject jsonInvoice = new JSONObject();
            jsonInvoice.put("invoice_id", invoice.getIdInvoice());
            jsonInvoice.put("date", invoice.getDate());
            jsonInvoice.put("invoiceSerialNumber", invoice.getInvoiceSerialNumber());
            jsonInvoice.put("invoiceNumber", invoice.getInvoiceNumber());
            jsonInvoice.put("totalBeforeDiscount", invoice.getTotalBeforeDiscount());
            jsonInvoice.put("discount", invoice.getDiscount());
            jsonInvoice.put("totalAfterDiscount", invoice.getTotalAfterDiscount());

            jsonInvoices.put(jsonInvoice);

            xmlBuilder.append("<invoice>")
                    .append("<invoice_id>").append(invoice.getIdInvoice()).append("</invoice_id>")
                    .append("<date>").append(invoice.getDate()).append("</date>")
                    .append("<invoiceSerialNumber>").append(invoice.getInvoiceSerialNumber()).append("</invoiceSerialNumber>")
                    .append("<invoiceNumber>").append(invoice.getInvoiceNumber()).append("</invoiceNumber>")
                    .append("<totalBeforeDiscount>").append(invoice.getTotalBeforeDiscount()).append("</totalBeforeDiscount>")
                    .append("<discount>").append(invoice.getDiscount()).append("</discount>")
                    .append("<totalAfterDiscount>").append(invoice.getTotalAfterDiscount()).append("</totalAfterDiscount>")
                    .append("</invoice>");
        }

        xmlBuilder.append("</invoices>");

        // Prepare final JSON response containing both JSON and XML content
        JSONObject finalResponse = new JSONObject();
        finalResponse.put("Jsoncontent", new JSONObject().put("invoices", jsonInvoices));
        finalResponse.put("Xmlcontent", xmlBuilder.toString());

        sendResponse(exchange, 200, finalResponse.toString());
    }

    // Handle GET request to retrieve a single invoice by ID
    private void handleGetById(HttpExchange exchange, int invoiceId) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice == null) {
            sendResponse(exchange, 404, "Invoice not found");
            return;
        }

        // Convert the invoice to JSON
        JSONObject jsonInvoice = new JSONObject();
        jsonInvoice.put("invoice_id", invoice.getIdInvoice());
        jsonInvoice.put("date", invoice.getDate());
        jsonInvoice.put("invoiceSerialNumber", invoice.getInvoiceSerialNumber());
        jsonInvoice.put("invoiceNumber", invoice.getInvoiceNumber());
        jsonInvoice.put("totalBeforeDiscount", invoice.getTotalBeforeDiscount());
        jsonInvoice.put("discount", invoice.getDiscount());
        jsonInvoice.put("totalAfterDiscount", invoice.getTotalAfterDiscount());

        // Convert the invoice to XML
        String xml = "<invoices><invoice>"
                + "<invoice_id>" + invoice.getIdInvoice() + "</invoice_id>"
                + "<date>" + invoice.getDate() + "</date>"
                + "<invoiceSerialNumber>" + invoice.getInvoiceSerialNumber() + "</invoiceSerialNumber>"
                + "<invoiceNumber>" + invoice.getInvoiceNumber() + "</invoiceNumber>"
                + "<totalBeforeDiscount>" + invoice.getTotalBeforeDiscount() + "</totalBeforeDiscount>"
                + "<discount>" + invoice.getDiscount() + "</discount>"
                + "<totalAfterDiscount>" + invoice.getTotalAfterDiscount() + "</totalAfterDiscount>"
                + "</invoice></invoices>";

        JSONObject finalResponse = new JSONObject();
        finalResponse.put("Jsoncontent", jsonInvoice);
        finalResponse.put("Xmlcontent", xml);

        sendResponse(exchange, 200, finalResponse.toString());
    }

    // Utility method to send HTTP response
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
