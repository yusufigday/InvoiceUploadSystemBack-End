package org.InvoiceUpload.worker;

import org.InvoiceUpload.repository.ItemRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        try {
            // HTTP server instance is created on port 8080 and started
            MyHttpServer server = new MyHttpServer(8080);
            server.start();

            // Repository instance created (could be used later for direct DB operations)
            ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

        } catch (Exception e) {
            // Print any startup errors
            System.out.println("Error occurred while starting the server:\n " + e.getMessage());
        }
    }
}
