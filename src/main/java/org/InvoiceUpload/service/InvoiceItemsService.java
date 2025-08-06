package org.InvoiceUpload.service;

import org.InvoiceUpload.model.InvoiceItems;
import org.InvoiceUpload.repository.InvoiceItemsRepository;
import org.InvoiceUpload.repository.InvoiceItemsRepositoryImpl;

import java.util.List;

public class InvoiceItemsService {
    private InvoiceItemsRepository invoiceItemsRepository = new InvoiceItemsRepositoryImpl();

    public boolean addInvoiceItem(InvoiceItems invoiceItems) {
        try{
            return invoiceItemsRepository.insert(invoiceItems) > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public List<InvoiceItems> getAllInvoiceItems() {
        return invoiceItemsRepository.getAllInvoiceItems();
    }

}
