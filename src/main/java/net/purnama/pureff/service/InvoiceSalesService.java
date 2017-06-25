/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceSalesDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
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
    
    @Transactional
    public List getInvoiceSalesList(int itemperpage, int page, String sort, String keyword){
        return invoicesalesDao.getInvoiceSalesList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countInvoiceSalesList(String keyword){
        return invoicesalesDao.countInvoiceSalesList(keyword);
    }
    
    @Transactional
    public List getUnpaidInvoiceSalesList(PartnerEntity partner,
            CurrencyEntity currency){
        return invoicesalesDao.getUnpaidInvoiceSalesList(partner, currency);
    }
    
    @Transactional
    public List getInvoiceSalesList(Calendar start, Calendar end, WarehouseEntity warehouse,
            PartnerEntity partner, CurrencyEntity currency){
        return invoicesalesDao.getInvoiceSalesList(start, end, warehouse, partner, currency);
    }
}

