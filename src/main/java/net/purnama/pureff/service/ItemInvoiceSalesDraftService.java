/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceSalesDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicesalesdraftService")
public class ItemInvoiceSalesDraftService {
    
    @Autowired
    ItemInvoiceSalesDraftDao iteminvoicesalesdraftDao;
    
    @Transactional
    public void addItemInvoiceSalesDraft(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft) {
        iteminvoicesalesdraftDao.addItemInvoiceSalesDraft(iteminvoicesalesdraft);
    }
    
    @Transactional
    public void updateItemInvoiceSalesDraft(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft) {
        iteminvoicesalesdraftDao.updateItemInvoiceSalesDraft(iteminvoicesalesdraft);
    }
    
    @Transactional
    public void deleteItemInvoiceSalesDraft(String id) {
        iteminvoicesalesdraftDao.deleteItemInvoiceSalesDraft(id);
    }
    
    @Transactional
    public ItemInvoiceSalesDraftEntity getItemInvoiceSalesDraft(String id) {
        return iteminvoicesalesdraftDao.getItemInvoiceSalesDraft(id);
    }
    
    @Transactional
    public List<ItemInvoiceSalesDraftEntity> getItemInvoiceSalesDraftList(InvoiceSalesDraftEntity invoicesalesdraft) {
        return iteminvoicesalesdraftDao.getItemInvoiceSalesDraftList(invoicesalesdraft);
    }
}
