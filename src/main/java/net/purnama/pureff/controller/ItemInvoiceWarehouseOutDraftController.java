/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutDraftService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class ItemInvoiceWarehouseOutDraftController {
    @Autowired
    ItemInvoiceWarehouseOutDraftService iteminvoicewarehouseoutdraftService;
    
    @Autowired
    InvoiceWarehouseOutDraftService invoicewarehouseoutdraftService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseOutDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoiceWarehouseOutDraftEntity> getItemInvoiceWarehouseOutDraftList(@PathVariable String id) {
        InvoiceWarehouseOutDraftEntity ad = invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraft(id);
        
        return iteminvoicewarehouseoutdraftService.getItemInvoiceWarehouseOutDraftList(ad);
    }
}
