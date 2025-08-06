package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.service.ItemService;
import org.json.JSONObject;

import java.io.*;

public class ItemController implements HttpHandler {

    private ItemService itemService=new ItemService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }



    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc,line) -> acc + line);

        JSONObject json = new JSONObject(body);

        int idItems =  json.getInt("items_id");
        String urunAdi = json.getString("name");
        int price = json.getInt("price");

        Item item = new Item(idItems, urunAdi, price);
        boolean success = itemService.addItem(item);

        String response = success ? "Item added." : " An error occurred while adding the item.";
        byte[] responseBytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();

    }

    private void handleGet(HttpExchange exchange) throws IOException {
        var items = itemService.getAllItems();

        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for (Item item : items) {
            JSONObject json  = new JSONObject();
            json.put("id_items", item.getIdItems());
            json.put("urun_adi", item.getName());
            json.put("price", item.getPrice());
            jsonArray.put(json);
        }

        byte[] responseBytes = jsonArray.toString().getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();


    }
}
