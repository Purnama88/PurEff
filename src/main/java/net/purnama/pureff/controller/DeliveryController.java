/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.DeliveryService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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
public class DeliveryController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    DeliveryService deliveryService;
    
    @RequestMapping(value = "api/getDeliveryList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getDeliveryList() {
        
        List<DeliveryEntity> ls = deliveryService.getDeliveryList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getDelivery", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getDelivery(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(deliveryService.getDelivery(id));
    }

    @RequestMapping(value = "api/updateDelivery", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateDelivery(@RequestBody DeliveryEntity delivery) {
        deliveryService.updateDelivery(delivery);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getDeliveryList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getDeliveryList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<DeliveryEntity> ls = deliveryService.
                getDeliveryList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countDeliveryList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countDeliveryList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(deliveryService.countDeliveryList(keyword));
    }
    
    @RequestMapping(value = {"api/cancelDelivery"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelDelivery(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        DeliveryEntity delivery = deliveryService.getDelivery(id);
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        delivery.setLastmodified(Calendar.getInstance());
        delivery.setWarehouse(warehouse);
        delivery.setLastmodifiedby(user);
        delivery.setStatus(false);
        
        deliveryService.updateDelivery(delivery);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getDeliveryList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "status"})
    public ResponseEntity<?> getDeliveryList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="status") boolean status){
        
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        List<DeliveryEntity> ls = deliveryService.
                getDeliveryList(start, end, warehouse, status);
        
        return ResponseEntity.ok(ls);
    }
}
