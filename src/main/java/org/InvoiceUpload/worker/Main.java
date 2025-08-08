package org.InvoiceUpload.worker;

import org.InvoiceUpload.repository.ItemRepositoryImpl;
import org.InvoiceUpload.service.ItemService;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        try {
            MyHttpServer server = new MyHttpServer(8080);
            server.start();
            ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();
        }catch (Exception e){
            System.out.println("Error occurred while starting the server:\n " + e.getMessage());
        }
    }
}
