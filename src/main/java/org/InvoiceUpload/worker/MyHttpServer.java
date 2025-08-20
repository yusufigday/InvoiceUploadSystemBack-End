package org.InvoiceUpload.worker;

import com.sun.net.httpserver.HttpServer;
import org.InvoiceUpload.controller.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer {

    private HttpServer server;

    public MyHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/items", new ItemController());
        server.createContext("/invoice", new InvoiceController());
        server.createContext("/customers", new CustomerController());
        server.createContext("/invoiceitems", new InvoiceItemsController());
        server.createContext("/addinvoice", new AddInvoiceController());
        server.createContext("/deleteinvoice", new DeleteInvoiceController());


        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("HTTP Server Port " + server.getAddress().getPort() + " was launched on!");
    }
}
