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
public class ItemInvoiceWarehouseOutDraftController {
    @Autowired
    ItemInvoiceWarehouseOutDraftService iteminvoicewarehoseoutdraftService;
    
    @Autowired
    InvoiceWarehouseOutDraftService invoicewarehoseoutdraftService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseOutDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceWarehouseOutDraftList(@RequestParam(value="id") String id) {
        InvoiceWarehouseOutDraftEntity ad = invoicewarehoseoutdraftService.getInvoiceWarehouseOutDraft(id);
        
        return ResponseEntity.ok(iteminvoicewarehoseoutdraftService.getItemInvoiceWarehouseOutDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemInvoiceWarehouseOutDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemInvoiceWarehouseOutDraftList(
            @RequestBody List<ItemInvoiceWarehouseOutDraftEntity> iteminvoicewarehoseoutdraftlist) {
        
        for(ItemInvoiceWarehouseOutDraftEntity iteminvoicewarehoseoutdraft : iteminvoicewarehoseoutdraftlist){
            if(iteminvoicewarehoseoutdraft.getItem() != null){
                if(iteminvoicewarehoseoutdraft.getId() != null){
                    iteminvoicewarehoseoutdraftService.updateItemInvoiceWarehouseOutDraft(iteminvoicewarehoseoutdraft);
                }
                else{
                    iteminvoicewarehoseoutdraft.setId(IdGenerator.generateId());
                    iteminvoicewarehoseoutdraftService.addItemInvoiceWarehouseOutDraft(iteminvoicewarehoseoutdraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemInvoiceWarehouseOutDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemInvoiceWarehouseOutDraftList(
            @RequestBody List<ItemInvoiceWarehouseOutDraftEntity> iteminvoicewarehoseoutdraftlist){
        
        for(ItemInvoiceWarehouseOutDraftEntity iteminvoicewarehoseoutdraft : iteminvoicewarehoseoutdraftlist){
            if(iteminvoicewarehoseoutdraft.getId() != null){
                iteminvoicewarehoseoutdraftService.deleteItemInvoiceWarehouseOutDraft(iteminvoicewarehoseoutdraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}