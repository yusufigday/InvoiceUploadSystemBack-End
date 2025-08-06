package org.InvoiceUpload.worker;

public class Main {
    public static void main(String[] args) {
        try {
            MyHttpServer server = new MyHttpServer(8080);
            server.start();
        }catch (Exception e){
            System.out.println("Error occurred while starting the server:\n " + e.getMessage());
        }
    }
}
