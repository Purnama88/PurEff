/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceSalesDraftEntity;
import net.purnama.pureff.service.InvoiceSalesDraftService;
import net.purnama.pureff.service.ItemInvoiceSalesDraftService;
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
public class ItemInvoiceSalesDraftController {
    @Autowired
    ItemInvoiceSalesDraftService iteminvoicesalesdraftService;
    
    @Autowired
    InvoiceSalesDraftService invoicesalesdraftService;
    
    @RequestMapping(value = "api/getItemInvoiceSalesDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceSalesDraftList(@RequestParam(value="id") String id) {
        InvoiceSalesDraftEntity ad = invoicesalesdraftService.getInvoiceSalesDraft(id);
        
        return ResponseEntity.ok(iteminvoicesalesdraftService.getItemInvoiceSalesDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemInvoiceSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemInvoiceSalesDraftList(
            @RequestBody List<ItemInvoiceSalesDraftEntity> iteminvoicesalesdraftlist) {
        
        for(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft : iteminvoicesalesdraftlist){
            if(iteminvoicesalesdraft.getItem() != null){
                if(iteminvoicesalesdraft.getId() != null){
                    iteminvoicesalesdraftService.updateItemInvoiceSalesDraft(iteminvoicesalesdraft);
                }
                else{
                    iteminvoicesalesdraft.setId(IdGenerator.generateId());
                    iteminvoicesalesdraftService.addItemInvoiceSalesDraft(iteminvoicesalesdraft);
                }
            }
        }
        
        return ResponseEntity.ok(iteminvoicesalesdraftlist);
    }
    
    @RequestMapping(value = "api/deleteItemInvoiceSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemInvoiceSalesDraftList(
            @RequestBody List<ItemInvoiceSalesDraftEntity> iteminvoicesalesdraftlist){
        
        for(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft : iteminvoicesalesdraftlist){
            if(iteminvoicesalesdraft.getId() != null){
                iteminvoicesalesdraftService.deleteItemInvoiceSalesDraft(iteminvoicesalesdraft.getId());
            }
            else{
                
            }
        }
        
        return ResponseEntity.ok("");
    }
}