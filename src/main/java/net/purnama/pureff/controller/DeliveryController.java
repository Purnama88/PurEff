/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.service.DeliveryService;
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
public class DeliveryController {
    
    @Autowired
    DeliveryService deliveryService;
    
    @RequestMapping(value = "api/getDeliveryList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<DeliveryEntity> getDeliveryList() {
        
        List<DeliveryEntity> ls = deliveryService.getDeliveryList();
        return ls;
    }
    
    @RequestMapping(value = "api/getDelivery/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public DeliveryEntity getDelivery(@PathVariable String id) {
        return deliveryService.getDelivery(id);
    }

    @RequestMapping(value = "api/addDelivery", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public DeliveryEntity addDelivery(@RequestBody DeliveryEntity delivery) {
        delivery.setId(IdGenerator.generateId());
        delivery.setLastmodified(Calendar.getInstance());
        
        deliveryService.addDelivery(delivery);
        
        return delivery;
    }

    @RequestMapping(value = "api/updateDelivery", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateDelivery(@RequestBody DeliveryEntity delivery) {
        deliveryService.updateDelivery(delivery);
    }
}
