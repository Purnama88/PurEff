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
public class ItemWarehouseController {
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    ItemService itemService;
    
    @RequestMapping(value = "api/getItemWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ItemWarehouseEntity> getItemWarehouseList(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.getItemWarehouseList(warehouse);
        return ls;
    }
    
    @RequestMapping(value = "api/getItemWarehouse/{itemid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ItemWarehouseEntity getItemWarehouse(HttpServletRequest httpRequest, @PathVariable String itemid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        ItemEntity item = itemService.getItem(itemid);
        
        return itemwarehouseService.getItemWarehouse(warehouse, item);
    }
}
