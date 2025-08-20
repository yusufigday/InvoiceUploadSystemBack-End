package org.InvoiceUpload.worker;

import com.sun.net.httpserver.HttpServer;
import org.InvoiceUpload.controller.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {

    private HttpServer server;

    public MyHttpServer(int port) throws IOException {
        // Create HTTP server on the specified port
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Define routes/contexts for different controllers
        server.createContext("/items", new ItemController());            // Handle item operations
        server.createContext("/invoice", new InvoiceController());       // Handle invoice operations
        server.createContext("/customers", new CustomerController());    // Handle customer operations
        server.createContext("/invoiceitems", new InvoiceItemsController()); // Handle invoice items
        server.createContext("/addinvoice", new AddInvoiceController());    // Handle adding invoices
        server.createContext("/deleteinvoice", new DeleteInvoiceController()); // Handle deleting invoices

        // Default executor (creates a new thread for each request)
        server.setExecutor(null);
    }

    public void start() {
        // Start the HTTP server
        server.start();
        System.out.println("HTTP Server Port " + server.getAddress().getPort() + " was launched on!");
    }
}
