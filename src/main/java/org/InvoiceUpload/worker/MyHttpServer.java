package org.InvoiceUpload.worker;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public class MyHttpServer {

    private HttpServer server;

    public MyHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("HTTP Sunucu " + server.getAddress().getPort() + " portunda başlatıldı!");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            System.out.println("İstek Metodu: " + method);

            System.out.println("Başlıklar:");
            for (Map.Entry<String, List<String>> header : exchange.getRequestHeaders().entrySet()) {
                System.out.println(header.getKey() + ": " + header.getValue());
            }

            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .reduce("", (acc, line) -> acc + line + "\n");

            System.out.println("İstek Gövdesi (Body):");
            System.out.println(body);
            String response = "İstek alındı. Teşekkürler!";
            byte[] responseBytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }

}
