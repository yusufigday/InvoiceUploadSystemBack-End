package org.InvoiceUpload.Converter;

import org.InvoiceUpload.model.Item;

import java.util.List;

public class XmlConverter {

    public static String itemToXml(Item item) {
        return "<item>" +
                "<id>" + item.getIdItems() + "<id>" +
                "<name>" + item.getName() + "</name>" +
                "<price>" + item.getPrice() + "</price>" +
                "</item>";
    }

    public static String itemsToXml(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("<items>");
        for (Item item : items) {
            sb.append(itemToXml(item));
        }
        sb.append("</items>");
        return sb.toString();
    }
}
