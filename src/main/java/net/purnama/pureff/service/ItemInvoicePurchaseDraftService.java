/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoicePurchaseDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoicePurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicepurchasedraftService")
public class ItemInvoicePurchaseDraftService {
    
    @Autowired
    ItemInvoicePurchaseDraftDao iteminvoicepurchasedraftDao;
    
    @Transactional
    public void addItemInvoicePurchaseDraft(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft) {
        iteminvoicepurchasedraftDao.addItemInvoicePurchaseDraft(iteminvoicepurchasedraft);
    }
    
    @Transactional
    public void updateItemInvoicePurchaseDraft(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft) {
        iteminvoicepurchasedraftDao.updateItemInvoicePurchaseDraft(iteminvoicepurchasedraft);
    }
    
    @Transactional
    public void deleteItemInvoicePurchaseDraft(String id) {
        iteminvoicepurchasedraftDao.deleteItemInvoicePurchaseDraft(id);
    }
    
    @Transactional
    public ItemInvoicePurchaseDraftEntity getItemInvoicePurchaseDraft(String id) {
        return iteminvoicepurchasedraftDao.getItemInvoicePurchaseDraft(id);
    }
    
    @Transactional
    public List<ItemInvoicePurchaseDraftEntity> getItemInvoicePurchaseDraftList(InvoicePurchaseDraftEntity invoicepurchasedraft) {
        return iteminvoicepurchasedraftDao.getItemInvoicePurchaseDraftList(invoicepurchasedraft);
    }
}
