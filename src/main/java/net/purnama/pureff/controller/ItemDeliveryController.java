/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;
import net.purnama.pureff.service.DeliveryService;
import net.purnama.pureff.service.ItemDeliveryService;
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
public class ItemDeliveryController {
    @Autowired
    ItemDeliveryService itemdeliveryService;
    
    @Autowired
    DeliveryService deliveryService;
    
    @RequestMapping(value = "api/getItemDeliveryList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemDeliveryEntity> getItemDeliveryList(@PathVariable String id) {
        DeliveryEntity ad = deliveryService.getDelivery(id);
        
        return itemdeliveryService.getItemDeliveryList(ad);
    }
}