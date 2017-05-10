/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceWarehouseInDao;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicewarehouseinService")
public class InvoiceWarehouseInService {
    
    @Autowired
    InvoiceWarehouseInDao invoicewarehouseinDao;

    @Transactional
    public List<InvoiceWarehouseInEntity> getInvoiceWarehouseInList() {
            return invoicewarehouseinDao.getInvoiceWarehouseInList();
    }

    @Transactional
    public InvoiceWarehouseInEntity getInvoiceWarehouseIn(String id) {
            return invoicewarehouseinDao.getInvoiceWarehouseIn(id);
    }

    @Transactional
    public void addInvoiceWarehouseIn(InvoiceWarehouseInEntity invoicewarehousein) {
            invoicewarehouseinDao.addInvoiceWarehouseIn(invoicewarehousein);
    }

    @Transactional
    public void updateInvoiceWarehouseIn(InvoiceWarehouseInEntity invoicewarehousein) {
            invoicewarehouseinDao.updateInvoiceWarehouseIn(invoicewarehousein);
    }
    
    @Transactional
    public List getInvoiceWarehouseInList(int itemperpage, int page, String sort, String keyword){
        return invoicewarehouseinDao.getInvoiceWarehouseInList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countInvoiceWarehouseInList(String keyword){
        return invoicewarehouseinDao.countInvoiceWarehouseInList(keyword);
    }
}