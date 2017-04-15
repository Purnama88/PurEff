/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceWarehouseOutDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicewarehouseoutdraftService")
public class InvoiceWarehouseOutDraftService {
    
    @Autowired
    InvoiceWarehouseOutDraftDao invoicewarehouseindraftDao;

    @Transactional
    public List<InvoiceWarehouseOutDraftEntity> getInvoiceWarehouseOutDraftList() {
            return invoicewarehouseindraftDao.getInvoiceWarehouseOutDraftList();
    }

    @Transactional
    public InvoiceWarehouseOutDraftEntity getInvoiceWarehouseOutDraft(String id) {
            return invoicewarehouseindraftDao.getInvoiceWarehouseOutDraft(id);
    }

    @Transactional
    public void addInvoiceWarehouseOutDraft(InvoiceWarehouseOutDraftEntity invoicewarehouseindraft) {
            invoicewarehouseindraftDao.addInvoiceWarehouseOutDraft(invoicewarehouseindraft);
    }

    @Transactional
    public void updateInvoiceWarehouseOutDraft(InvoiceWarehouseOutDraftEntity invoicewarehouseindraft) {
            invoicewarehouseindraftDao.updateInvoiceWarehouseOutDraft(invoicewarehouseindraft);
    }

    @Transactional
    public void deleteInvoiceWarehouseOutDraft(String id) {
            invoicewarehouseindraftDao.deleteInvoiceWarehouseOutDraft(id);
    }
}