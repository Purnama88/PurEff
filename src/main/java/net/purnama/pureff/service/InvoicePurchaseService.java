/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoicePurchaseDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicepurchaseService")
public class InvoicePurchaseService {
    
    @Autowired
    InvoicePurchaseDao invoicepurchaseDao;

    @Transactional
    public List<InvoicePurchaseEntity> getInvoicePurchaseList() {
            return invoicepurchaseDao.getInvoicePurchaseList();
    }

    @Transactional
    public InvoicePurchaseEntity getInvoicePurchase(String id) {
            return invoicepurchaseDao.getInvoicePurchase(id);
    }

    @Transactional
    public void addInvoicePurchase(InvoicePurchaseEntity invoicepurchase) {
            invoicepurchaseDao.addInvoicePurchase(invoicepurchase);
    }

    @Transactional
    public void updateInvoicePurchase(InvoicePurchaseEntity invoicepurchase) {
            invoicepurchaseDao.updateInvoicePurchase(invoicepurchase);
    }
    
    @Transactional
    public List getInvoicePurchaseList(int itemperpage, int page, String sort, String keyword){
        return invoicepurchaseDao.getInvoicePurchaseList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countInvoicePurchaseList(String keyword){
        return invoicepurchaseDao.countInvoicePurchaseList(keyword);
    }
    
    @Transactional
    public List getUnpaidInvoicePurchaseList(PartnerEntity partner,
            CurrencyEntity currency){
        return invoicepurchaseDao.getUnpaidInvoicePurchaseList(partner, currency);
    }
    
    @Transactional
    public List getInvoicePurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse,
            PartnerEntity partner, CurrencyEntity currency, boolean status){
        return invoicepurchaseDao.getInvoicePurchaseList(start, end, warehouse, partner, currency, status);
    }
}