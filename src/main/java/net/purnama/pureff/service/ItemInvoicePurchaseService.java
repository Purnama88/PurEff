/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoicePurchaseDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicepurchaseService")
public class ItemInvoicePurchaseService {
    
    @Autowired
    ItemInvoicePurchaseDao iteminvoicepurchaseDao;
    
    @Transactional
    public void addItemInvoicePurchase(ItemInvoicePurchaseEntity iteminvoicepurchase) {
        iteminvoicepurchaseDao.addItemInvoicePurchase(iteminvoicepurchase);
    }
    
    @Transactional
    public List<ItemInvoicePurchaseEntity> getItemInvoicePurchaseList(InvoicePurchaseEntity invoicepurchase) {
        return iteminvoicepurchaseDao.getItemInvoicePurchaseList(invoicepurchase);
    }
    
    @Transactional
    public List<ItemInvoicePurchaseEntity>
         getItemInvoicePurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse,
                 PartnerEntity partner, CurrencyEntity currency, boolean status){
         return iteminvoicepurchaseDao.getItemInvoicePurchaseList(start, end, warehouse, partner,
                 currency, status);
    }
}