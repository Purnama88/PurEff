/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.SellPriceEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.SellPriceService;
import net.purnama.pureff.service.UomService;
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
public class SellPriceController {
    
    @Autowired
    SellPriceService sellpriceService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getSellPriceList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<SellPriceEntity> getSellPriceList() {
        
        List<SellPriceEntity> ls = sellpriceService.getSellPriceList();
        return ls;
    }
    
    @RequestMapping(value = "api/getSellPriceList/{itemid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<SellPriceEntity> getSellPriceList(@PathVariable String itemid) {
        
        ItemEntity item = itemService.getItem(itemid);
        
        List<SellPriceEntity> ls = sellpriceService.getSellPriceList(item);
        return ls;
    }
    
    @RequestMapping(value = "api/getSellPrice/{itemid}/{uomid}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public SellPriceEntity getSellPrice(@PathVariable String itemid, @PathVariable String uomid) {
        
        ItemEntity item = itemService.getItem(itemid);
        UomEntity uom = uomService.getUom(uomid);
        
        return sellpriceService.getSellPrice(item, uom);
    }
    
    @RequestMapping(value = "api/getSellPrice/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public SellPriceEntity getSellPrice(@PathVariable String id) {
        return sellpriceService.getSellPrice(id);
    }

    @RequestMapping(value = "api/addSellPrice", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public SellPriceEntity addSellPrice(@RequestBody SellPriceEntity sellprice) {
        sellprice.setId(IdGenerator.generateId());
        sellprice.setLastmodified(Calendar.getInstance());
        
        sellpriceService.addSellPrice(sellprice);
        
        return sellprice;
    }

    @RequestMapping(value = "api/updateSellPrice", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateSellPrice(@RequestBody SellPriceEntity sellprice) {
        sellpriceService.updateSellPrice(sellprice);
    }

    @RequestMapping(value = "api/deleteSellPrice/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteSellPrice(@PathVariable String id) {
        sellpriceService.deleteSellPrice(id);		
    }
}

