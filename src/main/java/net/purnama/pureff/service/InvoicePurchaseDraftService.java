/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoicePurchaseDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicepurchasedraftService")
public class InvoicePurchaseDraftService {
    
    @Autowired
    InvoicePurchaseDraftDao invoicepurchasedraftDao;

    @Transactional
    public List<InvoicePurchaseDraftEntity> getInvoicePurchaseDraftList() {
            return invoicepurchasedraftDao.getInvoicePurchaseDraftList();
    }

    @Transactional
    public InvoicePurchaseDraftEntity getInvoicePurchaseDraft(String id) {
            return invoicepurchasedraftDao.getInvoicePurchaseDraft(id);
    }

    @Transactional
    public void addInvoicePurchaseDraft(InvoicePurchaseDraftEntity invoicepurchasedraft) {
            invoicepurchasedraftDao.addInvoicePurchaseDraft(invoicepurchasedraft);
    }

    @Transactional
    public void updateInvoicePurchaseDraft(InvoicePurchaseDraftEntity invoicepurchasedraft) {
            invoicepurchasedraftDao.updateInvoicePurchaseDraft(invoicepurchasedraft);
    }

    @Transactional
    public void deleteInvoicePurchaseDraft(String id) {
            invoicepurchasedraftDao.deleteInvoicePurchaseDraft(id);
    }
}