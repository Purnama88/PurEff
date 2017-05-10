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
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
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
public class WarehouseController {
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "/getWarehouse_IdCode_List", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getWarehouse_IdCode_List() {
        List<WarehouseEntity> ls = warehouseService.getWarehouse_IdCode_List();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "/api/getWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getWarehouseList() {
        List<WarehouseEntity> ls = warehouseService.getWarehouseList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "/api/getActiveWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveWarehouseList() {
        List<WarehouseEntity> ls = warehouseService.getActiveWarehouseList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getWarehouse", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getWarehouse(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(warehouseService.getWarehouse(id));
    }
    
    @RequestMapping(value = "api/addWarehouse", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addWarehouse(HttpServletRequest httpRequest,
            @RequestBody WarehouseEntity warehouse) {
        
        if(warehouseService.getWarehouseByCode(warehouse.getCode()) != null){
            return ResponseEntity.badRequest().body("Code '" + warehouse.getCode() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        warehouse.setId(IdGenerator.generateId());
        warehouse.setLastmodified(Calendar.getInstance());
        warehouse.setLastmodifiedby(temp);
        warehouseService.addWarehouse(warehouse);
        
        List<ItemEntity> itemlist = itemService.getItemList();
        
        for(ItemEntity item : itemlist){
            ItemWarehouseEntity itemwarehouse = new ItemWarehouseEntity();
            itemwarehouse.setId(IdGenerator.generateId());
            itemwarehouse.setItem(item);
            itemwarehouse.setLastmodified(Calendar.getInstance());
            itemwarehouse.setLastmodifiedby(temp);
            itemwarehouse.setNote("");
            itemwarehouse.setStatus(true);
            itemwarehouse.setStock(0);
            itemwarehouse.setWarehouse(warehouse);
        
            itemwarehouseService.addItemWarehouse(itemwarehouse);
        }
        
        return ResponseEntity.ok(warehouse);
    }

    @RequestMapping(value = "api/updateWarehouse", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateWarehouse(HttpServletRequest httpRequest,
            @RequestBody WarehouseEntity warehouse) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        warehouse.setLastmodified(Calendar.getInstance());
        warehouse.setLastmodifiedby(temp);
        
        warehouseService.updateWarehouse(warehouse);
        
        return ResponseEntity.ok(warehouse);
    }
    
    @RequestMapping(value = "/api/getWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getWarehouseList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<WarehouseEntity> ls = warehouseService.getWarehouseList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countWarehouseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countWarehouseList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(warehouseService.countWarehouseList(keyword));
    }
}
