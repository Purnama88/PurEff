/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemDeliveryDraftEntity;
import net.purnama.pureff.service.DeliveryDraftService;
import net.purnama.pureff.service.ItemDeliveryDraftService;
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
public class ItemDeliveryDraftController {
    @Autowired
    ItemDeliveryDraftService itemdeliverydraftService;
    
    @Autowired
    DeliveryDraftService deliverydraftService;
    
    @RequestMapping(value = "api/getItemDeliveryDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemDeliveryDraftList(@RequestParam(value="id") String id) {
        DeliveryDraftEntity ad = deliverydraftService.getDeliveryDraft(id);
        
        return ResponseEntity.ok(itemdeliverydraftService.getItemDeliveryDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemDeliveryDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemDeliveryDraftList(
            @RequestBody List<ItemDeliveryDraftEntity> itemdeliverydraftlist) {
        
        for(ItemDeliveryDraftEntity itemdeliverydraft : itemdeliverydraftlist){
            if(!itemdeliverydraft.getDescription().isEmpty()){
                if(itemdeliverydraft.getId() != null){
                    itemdeliverydraftService.updateItemDeliveryDraft(itemdeliverydraft);
                }
                else{
                    itemdeliverydraft.setId(IdGenerator.generateId());
                    itemdeliverydraftService.addItemDeliveryDraft(itemdeliverydraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemDeliveryDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemDeliveryDraftList(
            @RequestBody List<ItemDeliveryDraftEntity> itemdeliverydraftlist){
        
        for(ItemDeliveryDraftEntity itemdeliverydraft : itemdeliverydraftlist){
            if(itemdeliverydraft.getId() != null){
                itemdeliverydraftService.deleteItemDeliveryDraft(itemdeliverydraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}