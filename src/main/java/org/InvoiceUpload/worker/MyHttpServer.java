package org.InvoiceUpload.worker;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.InvoiceUpload.db.SQLManager;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpServer;
import org.InvoiceUpload.controller.ItemController;
import org.InvoiceUpload.controller.ProductController;

import java.io.InputStream;
import java.net.InetSocketAddress;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class MyHttpServer {

    private HttpServer server;

    public MyHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/items", new ItemController());
        server.createContext("/products", new ProductController());

        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("HTTP Server Port " + server.getAddress().getPort() + " uzerinde baslatildi!");
    }
}
