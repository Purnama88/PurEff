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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value = "api/getItemDeliveryList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemDeliveryList(@RequestParam(value="id") String id) {
        DeliveryEntity ad = deliveryService.getDelivery(id);
        
        return ResponseEntity.ok(itemdeliveryService.getItemDeliveryList(ad));
    }
}