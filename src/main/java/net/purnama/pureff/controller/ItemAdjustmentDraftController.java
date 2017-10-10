/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemAdjustmentDraftEntity;
import net.purnama.pureff.service.AdjustmentDraftService;
import net.purnama.pureff.service.ItemAdjustmentDraftService;
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
public class ItemAdjustmentDraftController {
    @Autowired
    ItemAdjustmentDraftService itemadjustmentdraftService;
    
    @Autowired
    AdjustmentDraftService adjustmentdraftService;
    
    @RequestMapping(value = "api/getItemAdjustmentDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemAdjustmentDraftList(@RequestParam(value="id") String id) {
        AdjustmentDraftEntity ad = adjustmentdraftService.getAdjustmentDraft(id);
        
        return ResponseEntity.ok(itemadjustmentdraftService.getItemAdjustmentDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemAdjustmentDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemAdjustmentDraftList(
            @RequestBody List<ItemAdjustmentDraftEntity> itemadjustmentdraftlist) {
        
        for(ItemAdjustmentDraftEntity itemadjustmentdraft : itemadjustmentdraftlist){
            if(itemadjustmentdraft.getItem() != null){
                if(itemadjustmentdraft.getId() != null){
                    itemadjustmentdraftService.updateItemAdjustmentDraft(itemadjustmentdraft);
                }
                else{
                    itemadjustmentdraft.setId(IdGenerator.generateId());
                    itemadjustmentdraftService.addItemAdjustmentDraft(itemadjustmentdraft);
                }
            }
        }
        
        return ResponseEntity.ok(itemadjustmentdraftlist);
    }
    
    @RequestMapping(value = "api/deleteItemAdjustmentDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemAdjustmentDraftList(
            @RequestBody List<ItemAdjustmentDraftEntity> itemadjustmentdraftlist){
        
        for(ItemAdjustmentDraftEntity itemadjustmentdraft : itemadjustmentdraftlist){
            if(itemadjustmentdraft.getId() != null){
                itemadjustmentdraftService.deleteItemAdjustmentDraft(itemadjustmentdraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}
