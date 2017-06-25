/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.WarehouseService;
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
public class ItemWarehouseController {
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @RequestMapping(value = "api/getItemWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getItemWarehouseList(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.getItemWarehouseList(warehouse);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getItemWarehouse", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemid"})
    public ResponseEntity<?> getItemWarehouse(HttpServletRequest httpRequest,
            @RequestParam(value="itemid") String itemid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        ItemEntity item = itemService.getItem(itemid);
        
        return ResponseEntity.ok(itemwarehouseService.getItemWarehouse(warehouse, item));
    }
    
    @RequestMapping(value = "/api/getItemWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getItemWarehouseList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.getItemWarehouseList(warehouse,
                itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countItemWarehouseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countItemWarehouseList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        return ResponseEntity.ok(itemwarehouseService.countItemWarehouseList(warehouse, keyword));
    }
}
