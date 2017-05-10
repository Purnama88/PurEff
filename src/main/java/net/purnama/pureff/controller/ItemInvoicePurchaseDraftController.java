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
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value = "api/getItemInvoicePurchaseDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoicePurchaseDraftList(@RequestParam(value="id") String id) {
        InvoicePurchaseDraftEntity ad = invoicepurchasedraftService.getInvoicePurchaseDraft(id);
        
        return ResponseEntity.ok(iteminvoicepurchasedraftService.getItemInvoicePurchaseDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemInvoicePurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemInvoicePurchaseDraftList(
            @RequestBody List<ItemInvoicePurchaseDraftEntity> iteminvoicepurchasedraftlist) {
        
        for(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft : iteminvoicepurchasedraftlist){
            if(iteminvoicepurchasedraft.getItem() != null){
                if(iteminvoicepurchasedraft.getId() != null){
                    iteminvoicepurchasedraftService.updateItemInvoicePurchaseDraft(iteminvoicepurchasedraft);
                }
                else{
                    iteminvoicepurchasedraft.setId(IdGenerator.generateId());
                    iteminvoicepurchasedraftService.addItemInvoicePurchaseDraft(iteminvoicepurchasedraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemInvoicePurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemInvoicePurchaseDraftList(
            @RequestBody List<ItemInvoicePurchaseDraftEntity> iteminvoicepurchasedraftlist){
        
        for(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft : iteminvoicepurchasedraftlist){
            if(iteminvoicepurchasedraft.getId() != null){
                iteminvoicepurchasedraftService.deleteItemInvoicePurchaseDraft(iteminvoicepurchasedraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}