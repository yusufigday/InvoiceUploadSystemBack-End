package org.InvoiceUpload;

import org.InvoiceUpload.db.SQLManager;

import java.sql.SQLException;

public class InvoiceService {
    public static void main(String[] args) throws SQLException {

        SQLManager sqlManager = new SQLManager();
//        sqlManager.updateUser("INSERT INTO customers(Ad, Soyad, TCKN)" + "VALUES('Mehmet', 'Korkmaz', '23232323232')");
//        sqlManager.updateUser("Update users set email = 'yusufgun@gmail.com' where username = 'namhm'");
//        sqlManager.selectUser("Select * From customers");
//        sqlManager.updateUser("INSERT INTO items(urun_adi)" + "VALUES('Su')");
//        sqlManager.updateUser("INSERT INTO products(Adı,Fiyatı,Miktarı,Tutar)" +"VALUES('Su','10','2','20')");
//        sqlManager.updateUser("INSERT INTO products(Adı,Fiyatı,Miktarı,Tutar)" +"VALUES('Elma','2','5','10')");
//        sqlManager.updateUser("INSERT INTO products(Adı,Fiyatı,Miktarı,Tutar)" +"VALUES('Kalem','25','4','100')");
        sqlManager.updateUser("INSERT INTO receipe(receipeSeriNo, MusteriNo, UrunNo)" + "VALUES('A89C27', '324D890', '627')");

    }
}