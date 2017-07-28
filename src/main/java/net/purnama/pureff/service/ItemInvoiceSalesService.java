/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceSalesDao;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicesalesService")
public class ItemInvoiceSalesService {
    
    @Autowired
    ItemInvoiceSalesDao iteminvoicesalesDao;
    
    @Transactional
    public void addItemInvoiceSales(ItemInvoiceSalesEntity iteminvoicesales) {
        iteminvoicesalesDao.addItemInvoiceSales(iteminvoicesales);
    }
    
    @Transactional
    public List<ItemInvoiceSalesEntity> getItemInvoiceSalesList(InvoiceSalesEntity invoicesales) {
        return iteminvoicesalesDao.getItemInvoiceSalesList(invoicesales);
    }
    
    @Transactional
    public List<ItemInvoiceSalesEntity>
         getItemInvoiceSalesList(Calendar start, Calendar end, WarehouseEntity warehouse, boolean status){
             return iteminvoicesalesDao.getItemInvoiceSalesList(start, end, warehouse, status);
         }
}