/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceWarehouseOutDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicewarehouseoutdraftService")
public class ItemInvoiceWarehouseOutDraftService {
    
    @Autowired
    ItemInvoiceWarehouseOutDraftDao iteminvoicewarehouseoutdraftDao;
    
    @Transactional
    public List<ItemInvoiceWarehouseOutDraftEntity> getItemInvoiceWarehouseOutDraftList(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        return iteminvoicewarehouseoutdraftDao.getItemInvoiceWarehouseOutDraftList(invoicewarehouseoutdraft);
    }
}