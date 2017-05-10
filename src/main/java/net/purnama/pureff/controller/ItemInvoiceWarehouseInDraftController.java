/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
import net.purnama.pureff.service.InvoiceWarehouseInDraftService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInDraftService;
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
public class ItemInvoiceWarehouseInDraftController {
    @Autowired
    ItemInvoiceWarehouseInDraftService iteminvoicewarehouseindraftService;
    
    @Autowired
    InvoiceWarehouseInDraftService invoicewarehouseindraftService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseInDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceWarehouseInDraftList(@RequestParam(value="id") String id) {
        InvoiceWarehouseInDraftEntity ad = invoicewarehouseindraftService.getInvoiceWarehouseInDraft(id);
        
        return ResponseEntity.ok(iteminvoicewarehouseindraftService.getItemInvoiceWarehouseInDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemInvoiceWarehouseInDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemInvoiceWarehouseInDraftList(
            @RequestBody List<ItemInvoiceWarehouseInDraftEntity> iteminvoicewarehouseindraftlist) {
        
        for(ItemInvoiceWarehouseInDraftEntity iteminvoicewarehouseindraft : iteminvoicewarehouseindraftlist){
            if(iteminvoicewarehouseindraft.getItem() != null){
                if(iteminvoicewarehouseindraft.getId() != null){
                    iteminvoicewarehouseindraftService.updateItemInvoiceWarehouseInDraft(iteminvoicewarehouseindraft);
                }
                else{
                    iteminvoicewarehouseindraft.setId(IdGenerator.generateId());
                    iteminvoicewarehouseindraftService.addItemInvoiceWarehouseInDraft(iteminvoicewarehouseindraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemInvoiceWarehouseInDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemInvoiceWarehouseInDraftList(
            @RequestBody List<ItemInvoiceWarehouseInDraftEntity> iteminvoicewarehouseindraftlist){
        
        for(ItemInvoiceWarehouseInDraftEntity iteminvoicewarehouseindraft : iteminvoicewarehouseindraftlist){
            if(iteminvoicewarehouseindraft.getId() != null){
                iteminvoicewarehouseindraftService.deleteItemInvoiceWarehouseInDraft(iteminvoicewarehouseindraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}