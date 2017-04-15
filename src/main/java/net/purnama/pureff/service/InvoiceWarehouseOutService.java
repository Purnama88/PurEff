/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceWarehouseOutDao;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicewarehouseoutService")
public class InvoiceWarehouseOutService {
    
    @Autowired
    InvoiceWarehouseOutDao invoicewarehouseinDao;

    @Transactional
    public List<InvoiceWarehouseOutEntity> getInvoiceWarehouseOutList() {
            return invoicewarehouseinDao.getInvoiceWarehouseOutList();
    }

    @Transactional
    public InvoiceWarehouseOutEntity getInvoiceWarehouseOut(String id) {
            return invoicewarehouseinDao.getInvoiceWarehouseOut(id);
    }

    @Transactional
    public void addInvoiceWarehouseOut(InvoiceWarehouseOutEntity invoicewarehousein) {
            invoicewarehouseinDao.addInvoiceWarehouseOut(invoicewarehousein);
    }

    @Transactional
    public void updateInvoiceWarehouseOut(InvoiceWarehouseOutEntity invoicewarehousein) {
            invoicewarehouseinDao.updateInvoiceWarehouseOut(invoicewarehousein);
    }
}