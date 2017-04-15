/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoicePurchaseDraftEntity;
import net.purnama.pureff.service.InvoicePurchaseDraftService;
import net.purnama.pureff.service.ItemInvoicePurchaseDraftService;
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
public class ItemInvoicePurchaseDraftController {
    @Autowired
    ItemInvoicePurchaseDraftService iteminvoicepurchasedraftService;
    
    @Autowired
    InvoicePurchaseDraftService invoicepurchasedraftService;
    
    @RequestMapping(value = "api/getItemInvoicePurchaseDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoicePurchaseDraftEntity> getItemInvoicePurchaseDraftList(@PathVariable String id) {
        InvoicePurchaseDraftEntity ad = invoicepurchasedraftService.getInvoicePurchaseDraft(id);
        
        return iteminvoicepurchasedraftService.getItemInvoicePurchaseDraftList(ad);
    }
}