/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceWarehouseOutDao;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicewarehouseoutService")
public class ItemInvoiceWarehouseOutService {
    
    @Autowired
    ItemInvoiceWarehouseOutDao iteminvoicewarehouseoutDao;
    
    @Transactional
    public void addItemInvoiceWarehouseOut(ItemInvoiceWarehouseOutEntity iteminvoicewarehouseout) {
        iteminvoicewarehouseoutDao.addItemInvoiceWarehouseOut(iteminvoicewarehouseout);
    }
    
    @Transactional
    public List<ItemInvoiceWarehouseOutEntity> getItemInvoiceWarehouseOutList(InvoiceWarehouseOutEntity invoicewarehouseout) {
        return iteminvoicewarehouseoutDao.getItemInvoiceWarehouseOutList(invoicewarehouseout);
    }
    
    @Transactional
    public List<ItemInvoiceWarehouseOutEntity>
         getItemInvoiceWarehouseOutList(Calendar start, Calendar end, 
                 WarehouseEntity warehouse, boolean status){
             return iteminvoicewarehouseoutDao.getItemInvoiceWarehouseOutList(start, end, warehouse, status);
         }
}