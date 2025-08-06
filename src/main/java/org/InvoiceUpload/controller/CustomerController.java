package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Customer;
import org.InvoiceUpload.service.CustomerService;
import org.json.JSONObject;

import java.io.*;

public class CustomerController implements HttpHandler {
    private CustomerService customerService = new CustomerService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        }else if(method.equalsIgnoreCase("GET")){
            handleGet(exchange);
        }else{
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handlePost (HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc , line) -> acc + line);

        JSONObject json = new JSONObject(body);

        int idCustomer = json.getInt("customers_id");
        String name = json.getString("name");
        String lastName = json.getString("lastName");
        String tckn = json.getString("TCKN");

        Customer customer = new Customer(idCustomer, name, lastName, tckn);
        boolean success = customerService.addCustomer(customer);

        String response = success ? "Customer Eklendi." : "Customer eklenirken hata oluştu.";
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private void handleGet (HttpExchange exchange) throws IOException {
        var customers = customerService.getAllCustomers();

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for(Customer c : customers){
            JSONObject json = new JSONObject();
            json.put("customers_id", c.getIdCustomer());
            json.put("name", c.getAdi());
            json.put("lastName", c.getSoyadi());
            json.put("TCKN", c.getTckn());
            jsonArray.put(json);
        }

        byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type" , "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
