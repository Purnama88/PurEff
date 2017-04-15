/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.InvoiceSalesDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("invoicesalesdraftService")
public class InvoiceSalesDraftService {
    
    @Autowired
    InvoiceSalesDraftDao invoicesalesdraftDao;

    @Transactional
    public List<InvoiceSalesDraftEntity> getInvoiceSalesDraftList() {
            return invoicesalesdraftDao.getInvoiceSalesDraftList();
    }

    @Transactional
    public InvoiceSalesDraftEntity getInvoiceSalesDraft(String id) {
            return invoicesalesdraftDao.getInvoiceSalesDraft(id);
    }

    @Transactional
    public void addInvoiceSalesDraft(InvoiceSalesDraftEntity invoicesalesdraft) {
            invoicesalesdraftDao.addInvoiceSalesDraft(invoicesalesdraft);
    }

    @Transactional
    public void updateInvoiceSalesDraft(InvoiceSalesDraftEntity invoicesalesdraft) {
            invoicesalesdraftDao.updateInvoiceSalesDraft(invoicesalesdraft);
    }

    @Transactional
    public void deleteInvoiceSalesDraft(String id) {
            invoicesalesdraftDao.deleteInvoiceSalesDraft(id);
    }
}

