/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.SellPriceEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.SellPriceService;
import net.purnama.pureff.service.UomService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SellPriceController {
    
    @Autowired
    SellPriceService sellpriceService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getSellPriceList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getSellPriceList() {
        
        List<SellPriceEntity> ls = sellpriceService.getSellPriceList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getSellPriceList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemid"})
    public ResponseEntity<?> getSellPriceList(@RequestParam(value="itemid") String itemid) {
        
        ItemEntity item = itemService.getItem(itemid);
        
        List<SellPriceEntity> ls = sellpriceService.getSellPriceList(item);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getSellPrice", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"itemid", "uomid"})
    public ResponseEntity<?> getSellPrice(@RequestParam(value="itemid") String itemid,
            @RequestParam(value="uomid") String uomid) {
        
        ItemEntity item = itemService.getItem(itemid);
        UomEntity uom = uomService.getUom(uomid);
        
        return ResponseEntity.ok(sellpriceService.getSellPrice(item, uom));
    }
    
    @RequestMapping(value = "api/updateSellPrice", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateSellPrice(@RequestBody SellPriceEntity sellprice) {
        sellpriceService.updateSellPrice(sellprice);
        
        return ResponseEntity.ok(sellprice);
    }

}

