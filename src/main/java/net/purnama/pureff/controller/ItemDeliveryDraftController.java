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
public class ItemDeliveryDraftController {
    @Autowired
    ItemDeliveryDraftService itemdeliverydraftService;
    
    @Autowired
    DeliveryDraftService deliverydraftService;
    
    @RequestMapping(value = "api/getItemDeliveryDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemDeliveryDraftEntity> getItemDeliveryDraftList(@PathVariable String id) {
        DeliveryDraftEntity ad = deliverydraftService.getDeliveryDraft(id);
        
        return itemdeliverydraftService.getItemDeliveryDraftList(ad);
    }
}