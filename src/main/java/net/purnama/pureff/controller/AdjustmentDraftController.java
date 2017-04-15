/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import net.purnama.pureff.service.AdjustmentDraftService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class AdjustmentDraftController {
    
    @Autowired
    AdjustmentDraftService adjustmentdraftService;
    
    @RequestMapping(value = "api/getAdjustmentDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<AdjustmentDraftEntity> getAdjustmentDraftList() {
        
        List<AdjustmentDraftEntity> ls = adjustmentdraftService.getAdjustmentDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getAdjustmentDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public AdjustmentDraftEntity getAdjustmentDraft(@PathVariable String id) {
        return adjustmentdraftService.getAdjustmentDraft(id);
    }

    @RequestMapping(value = "api/addAdjustmentDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public AdjustmentDraftEntity addAdjustmentDraft(@RequestBody AdjustmentDraftEntity adjustmentdraft) {
        adjustmentdraft.setId(IdGenerator.generateId());
        adjustmentdraft.setLastmodified(Calendar.getInstance());
        
        adjustmentdraftService.addAdjustmentDraft(adjustmentdraft);
        
        return adjustmentdraft;
    }

    @RequestMapping(value = "api/updateAdjustmentDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateAdjustmentDraft(@RequestBody AdjustmentDraftEntity adjustmentdraft) {
        adjustmentdraftService.updateAdjustmentDraft(adjustmentdraft);
    }

    @RequestMapping(value = "api/deleteAdjustmentDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteAdjustmentDraft(@PathVariable String id) {
        adjustmentdraftService.deleteAdjustmentDraft(id);		
    }
}
