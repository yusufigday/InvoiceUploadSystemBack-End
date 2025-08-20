package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Invoice;
import org.InvoiceUpload.service.InvoiceService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class InvoiceController implements HttpHandler {
    private InvoiceService invoiceService = new InvoiceService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {
            String path = exchange.getRequestURI().getPath();

            if (path.matches(".*/invoices/\\d+$")) {
                int invoiceId = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                handleGetById(exchange, invoiceId);
            } else {
                handleGetAll(exchange);
            }
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Invoice> invoices = invoiceService.getAllInvoice();
        JSONArray jsonInvoices = new JSONArray();
        StringBuilder xmlBuilder = new StringBuilder("<invoices>");

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

            xmlBuilder.append("<invoice>").append("<invoice_id>").append(invoice.getIdInvoice()).append("</invoice_id>").append("<date>").append(invoice.getDate()).append("</date>").append("<invoiceSerialNumber>").append(invoice.getInvoiceSerialNumber()).append("</invoiceSerialNumber>").append("<invoiceNumber>").append(invoice.getInvoiceNumber()).append("</invoiceNumber>").append("<totalBeforeDiscount>").append(invoice.getTotalBeforeDiscount()).append("</totalBeforeDiscount>").append("<discount>").append(invoice.getDiscount()).append("</discount>").append("<totalAfterDiscount>").append(invoice.getTotalAfterDiscount()).append("</totalAfterDiscount>").append("</invoice>");
        }

        xmlBuilder.append("</invoices>");

        JSONObject finalResponse = new JSONObject();
        finalResponse.put("Jsoncontent", new JSONObject().put("invoices", jsonInvoices));
        finalResponse.put("Xmlcontent", xmlBuilder.toString());

        sendResponse(exchange, 200, finalResponse.toString());
    }

    private void handleGetById(HttpExchange exchange, int invoiceId) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice == null) {
            sendResponse(exchange, 404, "Invoice not found");
            return;
        }

        JSONObject jsonInvoice = new JSONObject();
        jsonInvoice.put("invoice_id", invoice.getIdInvoice());
        jsonInvoice.put("date", invoice.getDate());
        jsonInvoice.put("invoiceSerialNumber", invoice.getInvoiceSerialNumber());
        jsonInvoice.put("invoiceNumber", invoice.getInvoiceNumber());
        jsonInvoice.put("totalBeforeDiscount", invoice.getTotalBeforeDiscount());
        jsonInvoice.put("discount", invoice.getDiscount());
        jsonInvoice.put("totalAfterDiscount", invoice.getTotalAfterDiscount());

        String xml = "<invoices><invoice>" + "<invoice_id>" + invoice.getIdInvoice() + "</invoice_id>" + "<date>" + invoice.getDate() + "</date>" + "<invoiceSerialNumber>" + invoice.getInvoiceSerialNumber() + "</invoiceSerialNumber>" + "<invoiceNumber>" + invoice.getInvoiceNumber() + "</invoiceNumber>" + "<totalBeforeDiscount>" + invoice.getTotalBeforeDiscount() + "</totalBeforeDiscount>" + "<discount>" + invoice.getDiscount() + "</discount>" + "<totalAfterDiscount>" + invoice.getTotalAfterDiscount() + "</totalAfterDiscount>" + "</invoice></invoices>";

        JSONObject finalResponse = new JSONObject();
        finalResponse.put("Jsoncontent", jsonInvoice);
        finalResponse.put("Xmlcontent", xml);

        sendResponse(exchange, 200, finalResponse.toString());
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
