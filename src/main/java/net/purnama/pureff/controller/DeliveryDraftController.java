/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import net.purnama.pureff.service.DeliveryDraftService;
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
public class DeliveryDraftController {
    
    @Autowired
    DeliveryDraftService deliverydraftService;
    
    @RequestMapping(value = "api/getDeliveryDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<DeliveryDraftEntity> getDeliveryDraftList() {
        
        List<DeliveryDraftEntity> ls = deliverydraftService.getDeliveryDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getDeliveryDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public DeliveryDraftEntity getDeliveryDraft(@PathVariable String id) {
        return deliverydraftService.getDeliveryDraft(id);
    }

    @RequestMapping(value = "api/addDeliveryDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public DeliveryDraftEntity addDeliveryDraft(@RequestBody DeliveryDraftEntity deliverydraft) {
        deliverydraft.setId(IdGenerator.generateId());
        deliverydraft.setLastmodified(Calendar.getInstance());
        
        deliverydraftService.addDeliveryDraft(deliverydraft);
        
        return deliverydraft;
    }

    @RequestMapping(value = "api/updateDeliveryDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateDeliveryDraft(@RequestBody DeliveryDraftEntity deliverydraft) {
        deliverydraftService.updateDeliveryDraft(deliverydraft);
    }

    @RequestMapping(value = "api/deleteDeliveryDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteDeliveryDraft(@PathVariable String id) {
        deliverydraftService.deleteDeliveryDraft(id);		
    }
}
