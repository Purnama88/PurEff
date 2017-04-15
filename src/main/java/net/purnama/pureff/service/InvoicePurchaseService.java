/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoicePurchaseDao;
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
}