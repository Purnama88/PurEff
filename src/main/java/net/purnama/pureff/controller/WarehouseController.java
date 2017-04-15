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
public class WarehouseController {
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "/getWarehouse_IdCode_List", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<WarehouseEntity> getWarehouse_IdCode_List() {
        List<WarehouseEntity> ls = warehouseService.getWarehouse_IdCode_List();
        return ls;
    }
    
    @RequestMapping(value = "/api/getWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<WarehouseEntity> getWarehouseList() {
        List<WarehouseEntity> ls = warehouseService.getWarehouseList();
        return ls;
    }
    
    @RequestMapping(value = "/api/getActiveWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<WarehouseEntity> getActiveWarehouseList() {
        List<WarehouseEntity> ls = warehouseService.getActiveWarehouseList();
        return ls;
    }
    
    @RequestMapping(value = "api/getWarehouse/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public WarehouseEntity getWarehouse(@PathVariable String id) {
        return warehouseService.getWarehouse(id);
    }
    
    @RequestMapping(value = "api/addWarehouse", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public void addWarehouse(HttpServletRequest httpRequest, @RequestBody WarehouseEntity warehouse) {
        
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
    }

    @RequestMapping(value = "api/updateWarehouse", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public WarehouseEntity updateWarehouse(HttpServletRequest httpRequest,
            @RequestBody WarehouseEntity warehouse) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        warehouse.setLastmodified(Calendar.getInstance());
        warehouse.setLastmodifiedby(temp);
        
        warehouseService.updateWarehouse(warehouse);
        
        return warehouse;
    }
}
