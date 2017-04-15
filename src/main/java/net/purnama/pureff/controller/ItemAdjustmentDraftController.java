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
public class ItemAdjustmentDraftController {
    @Autowired
    ItemAdjustmentDraftService itemadjustmentdraftService;
    
    @Autowired
    AdjustmentDraftService adjustmentdraftService;
    
    @RequestMapping(value = "api/getItemAdjustmentDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemAdjustmentDraftEntity> getItemAdjustmentDraftList(@PathVariable String id) {
        AdjustmentDraftEntity ad = adjustmentdraftService.getAdjustmentDraft(id);
        
        return itemadjustmentdraftService.getItemAdjustmentDraftList(ad);
    }
}
