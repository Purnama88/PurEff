/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceSalesDao;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicesalesService")
public class InvoiceSalesService {
    
    @Autowired
    InvoiceSalesDao invoicesalesDao;

    @Transactional
    public List<InvoiceSalesEntity> getInvoiceSalesList() {
            return invoicesalesDao.getInvoiceSalesList();
    }

    @Transactional
    public InvoiceSalesEntity getInvoiceSales(String id) {
            return invoicesalesDao.getInvoiceSales(id);
    }

    @Transactional
    public void addInvoiceSales(InvoiceSalesEntity invoicesales) {
            invoicesalesDao.addInvoiceSales(invoicesales);
    }

    @Transactional
    public void updateInvoiceSales(InvoiceSalesEntity invoicesales) {
            invoicesalesDao.updateInvoiceSales(invoicesales);
    }
}

