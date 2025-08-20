package org.InvoiceUpload.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.InvoiceUpload.model.Item;
import org.InvoiceUpload.service.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

// Controller to handle HTTP requests for items
public class ItemController implements HttpHandler {

    // Service to manage items
    private ItemService itemService = new ItemService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        // Handle POST and GET requests
        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    // Handle POST request: add a new item
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new BufferedReader(new InputStreamReader(is))
                .lines().reduce("", (acc, line) -> acc + line);

        JSONObject json = new JSONObject(body);

        String itemName = json.getString("name");
        int price = json.getInt("price");

        // Create Item object
        Item item = new Item(itemName, price);

        // Add item using service
        boolean success = itemService.addItem(item);

        String response = success ? "Item added." : "An error occurred while adding the item.";
        byte[] responseBytes = response.getBytes("UTF-8");

        // Send HTTP response
        exchange.sendResponseHeaders(success ? 200 : 500, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    // Handle GET request: retrieve all items
    private void handleGet(HttpExchange exchange) throws IOException {
        List<Item> items = itemService.getAllItems();

        JSONArray jsonItems = new JSONArray();
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<items>");

        // Prepare JSON response structure
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("items", jsonItems);

        JSONObject response = new JSONObject();
        response.put("Jsoncontent", jsonContent);
        response.put("Xmlcontent", xmlBuilder.toString());

        // Convert each item to JSON and XML
        for (Item item : items) {
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("id", item.getIdItems());
            jsonItem.put("name", item.getName());
            jsonItem.put("price", item.getPrice());
            jsonItems.put(jsonItem);

            xmlBuilder.append("<item>")
                    .append("<id>").append(item.getIdItems()).append("</id>")
                    .append("<name>").append(item.getName()).append("</name>")
                    .append("<price>").append(item.getPrice()).append("</price>")
                    .append("</item>");
        }

        xmlBuilder.append("</items>");

        // Send JSON response
        byte[] responseBytes = response.toString().getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
