/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceWarehouseInDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicewarehouseindraftService")
public class InvoiceWarehouseInDraftService {
    
    @Autowired
    InvoiceWarehouseInDraftDao invoicewarehouseindraftDao;

    @Transactional
    public List<InvoiceWarehouseInDraftEntity> getInvoiceWarehouseInDraftList() {
            return invoicewarehouseindraftDao.getInvoiceWarehouseInDraftList();
    }

    @Transactional
    public InvoiceWarehouseInDraftEntity getInvoiceWarehouseInDraft(String id) {
            return invoicewarehouseindraftDao.getInvoiceWarehouseInDraft(id);
    }

    @Transactional
    public void addInvoiceWarehouseInDraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
            invoicewarehouseindraftDao.addInvoiceWarehouseInDraft(invoicewarehouseindraft);
    }

    @Transactional
    public void updateInvoiceWarehouseInDraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
            invoicewarehouseindraftDao.updateInvoiceWarehouseInDraft(invoicewarehouseindraft);
    }

    @Transactional
    public void deleteInvoiceWarehouseInDraft(String id) {
            invoicewarehouseindraftDao.deleteInvoiceWarehouseInDraft(id);
    }
    
    @Transactional
    public List getInvoiceWarehouseInDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        return invoicewarehouseindraftDao.getInvoiceWarehouseInDraftList(itemperpage, page, sort, keyword, user);
    }
    
    @Transactional
    public int countInvoiceWarehouseInDraftList(String keyword, UserEntity user){{
        return invoicewarehouseindraftDao.countInvoiceWarehouseInDraftList(keyword, user);
    }
}}