/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.pureff.service.ReturnPurchaseDraftService;
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
public class ReturnPurchaseDraftController {
    
    @Autowired
    ReturnPurchaseDraftService returnpurchasedraftService;
    
    @RequestMapping(value = "api/getReturnPurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ReturnPurchaseDraftEntity> getReturnPurchaseDraftList() {
        
        List<ReturnPurchaseDraftEntity> ls = returnpurchasedraftService.getReturnPurchaseDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getReturnPurchaseDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ReturnPurchaseDraftEntity getReturnPurchaseDraft(@PathVariable String id) {
        return returnpurchasedraftService.getReturnPurchaseDraft(id);
    }

    @RequestMapping(value = "api/addReturnPurchaseDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ReturnPurchaseDraftEntity addReturnPurchaseDraft(@RequestBody ReturnPurchaseDraftEntity returnpurchasedraft) {
        returnpurchasedraft.setId(IdGenerator.generateId());
        returnpurchasedraft.setLastmodified(Calendar.getInstance());
        
        returnpurchasedraftService.addReturnPurchaseDraft(returnpurchasedraft);
        
        return returnpurchasedraft;
    }

    @RequestMapping(value = "api/updateReturnPurchaseDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateReturnPurchaseDraft(@RequestBody ReturnPurchaseDraftEntity returnpurchasedraft) {
        returnpurchasedraftService.updateReturnPurchaseDraft(returnpurchasedraft);
    }

    @RequestMapping(value = "api/deleteReturnPurchaseDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteReturnPurchaseDraft(@PathVariable String id) {
        returnpurchasedraftService.deleteReturnPurchaseDraft(id);		
    }
}

