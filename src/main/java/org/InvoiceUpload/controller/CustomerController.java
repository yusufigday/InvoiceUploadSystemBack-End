package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Customer;
import org.InvoiceUpload.service.CustomerService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

// Controller to handle customer-related HTTP requests
public class CustomerController implements HttpHandler {

    // Service for customer operations
    private CustomerService customerService = new CustomerService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        // Handle POST and GET requests separately
        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    // Handle POST request: add a new customer
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);

        String name = json.getString("name");
        String lastName = json.getString("lastName");
        String tckn = json.getString("TCKN");

        // Create a new Customer object
        Customer customer = new Customer(name, lastName, tckn);

        // Add customer using service
        boolean success = customerService.addCustomer(customer);

        String response = success ? "Customer added." : "An error occurred while adding a customer.\n";
        byte[] responseBytes = response.getBytes("UTF-8");

        // Send HTTP response
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    // Handle GET request: get all customers
    private void handleGet(HttpExchange exchange) throws IOException {
        var customers = customerService.getAllCustomers();

        JSONArray jsonArray = new JSONArray();
        for (Customer c : customers) {
            JSONObject json = new JSONObject();
            json.put("name", c.getFirstName());
            json.put("lastName", c.getLastName());
            json.put("TCKN", c.getTckn());
            jsonArray.put(json);
        }

        byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");

        // Set response headers and send JSON response
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
