/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;
import net.purnama.pureff.service.DeliveryService;
import net.purnama.pureff.service.ItemDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    
    @RequestMapping(value = {"api/getItemDeliveryList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "status"})
    public ResponseEntity<?> getItemDeliveryList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="status") boolean status){
        
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        List<ItemDeliveryEntity> ls = itemdeliveryService.
                getItemDeliveryList(start, end, warehouse, status);
        
        return ResponseEntity.ok(ls);
    }
}