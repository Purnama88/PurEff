/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.service.ItemAdjustmentService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdjustmentController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    AdjustmentService adjustmentService;
    
    @Autowired
    ItemAdjustmentService itemadjustmentService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "api/getAdjustmentList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getAdjustmentList() {
        
        List<AdjustmentEntity> ls = adjustmentService.getAdjustmentList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getAdjustment", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getAdjustment(@RequestParam(value="id") String id){
        return ResponseEntity.ok(adjustmentService.getAdjustment(id));
    }

    @RequestMapping(value = "api/updateAdjustment", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateAdjustment(HttpServletRequest httpRequest,
            @RequestBody AdjustmentEntity adjustment) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        adjustment.setLastmodified(Calendar.getInstance());
        adjustment.setWarehouse(warehouse);
        adjustment.setLastmodifiedby(user);
        
        adjustmentService.updateAdjustment(adjustment);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getAdjustmentList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getAdjustmentList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<AdjustmentEntity> ls = adjustmentService.
                getAdjustmentList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countAdjustmentList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countAdjustmentList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(adjustmentService.countAdjustmentList(keyword));
    }
    
    @RequestMapping(value = {"api/cancelAdjustment"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelAdjustment(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        AdjustmentEntity adjustment = adjustmentService.getAdjustment(id);
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = adjustment.getWarehouse();
        
        List<ItemAdjustmentEntity> ialist = 
                itemadjustmentService.getItemAdjustmentList(adjustment);
        
        for(ItemAdjustmentEntity itemadjustment : ialist) {
            
            ItemEntity item = itemadjustment.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(warehouse, item);
            
            iw.setStock(iw.getStock() - itemadjustment.getDiff());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        adjustment.setStatus(false);
        adjustment.setLastmodified(Calendar.getInstance());
        adjustment.setLastmodifiedby(user);
        
        adjustmentService.updateAdjustment(adjustment);
        
        return ResponseEntity.ok("");
    }
}
