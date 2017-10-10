/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnPurchaseDraftEntity;
import net.purnama.pureff.service.ReturnPurchaseDraftService;
import net.purnama.pureff.service.ItemReturnPurchaseDraftService;
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
public class ItemReturnPurchaseDraftController {
    @Autowired
    ItemReturnPurchaseDraftService itemreturnpurchasedraftService;
    
    @Autowired
    ReturnPurchaseDraftService returnpurchasedraftService;
    
    @RequestMapping(value = "api/getItemReturnPurchaseDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemReturnPurchaseDraftList(@RequestParam(value="id") String id){
        ReturnPurchaseDraftEntity ad = returnpurchasedraftService.getReturnPurchaseDraft(id);
        
        return ResponseEntity.ok(itemreturnpurchasedraftService.getItemReturnPurchaseDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemReturnPurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemReturnPurchaseDraftList(
            @RequestBody List<ItemReturnPurchaseDraftEntity> itemreturnpurchasedraftlist) {
        
        for(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft : itemreturnpurchasedraftlist){
            if(itemreturnpurchasedraft.getItem() != null){
                if(itemreturnpurchasedraft.getId() != null){
                    itemreturnpurchasedraftService.updateItemReturnPurchaseDraft(itemreturnpurchasedraft);
                }
                else{
                    itemreturnpurchasedraft.setId(IdGenerator.generateId());
                    itemreturnpurchasedraftService.addItemReturnPurchaseDraft(itemreturnpurchasedraft);
                }
            }
        }
        
        return ResponseEntity.ok(itemreturnpurchasedraftlist);
    }
    
    @RequestMapping(value = "api/deleteItemReturnPurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemReturnPurchaseDraftList(
            @RequestBody List<ItemReturnPurchaseDraftEntity> itemreturnpurchasedraftlist){
        
        for(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft : itemreturnpurchasedraftlist){
            if(itemreturnpurchasedraft.getId() != null){
                itemreturnpurchasedraftService.deleteItemReturnPurchaseDraft(itemreturnpurchasedraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}