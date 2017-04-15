/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceWarehouseInDao;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicewarehouseinService")
public class ItemInvoiceWarehouseInService {
    
    @Autowired
    ItemInvoiceWarehouseInDao iteminvoicewarehouseinDao;
    
    @Transactional
    public List<ItemInvoiceWarehouseInEntity> getItemInvoiceWarehouseInList(InvoiceWarehouseInEntity invoicewarehousein) {
        return iteminvoicewarehouseinDao.getItemInvoiceWarehouseInList(invoicewarehousein);
    }
}