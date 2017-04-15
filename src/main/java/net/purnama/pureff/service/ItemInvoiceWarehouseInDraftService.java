/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemInvoiceWarehouseInDraftDao;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("iteminvoicewarehouseindraftService")
public class ItemInvoiceWarehouseInDraftService {
    
    @Autowired
    ItemInvoiceWarehouseInDraftDao iteminvoicewarehouseindraftDao;
    
    @Transactional
    public List<ItemInvoiceWarehouseInDraftEntity> getItemInvoiceWarehouseInDraftList(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        return iteminvoicewarehouseindraftDao.getItemInvoiceWarehouseInDraftList(invoicewarehouseindraft);
    }
}
