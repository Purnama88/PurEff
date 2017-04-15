/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.BuyPriceEntity;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.service.BuyPriceService;
import net.purnama.pureff.service.ItemService;
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
public class BuyPriceController {
    
    @Autowired
    BuyPriceService buypriceService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getBuyPriceList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<BuyPriceEntity> getBuyPriceList() {
        
        List<BuyPriceEntity> ls = buypriceService.getBuyPriceList();
        return ls;
    }
    
    @RequestMapping(value = "api/getBuyPriceList/{itemid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<BuyPriceEntity> getBuyPriceList(@PathVariable String itemid) {
        
        ItemEntity item = itemService.getItem(itemid);
        
        List<BuyPriceEntity> ls = buypriceService.getBuyPriceList(item);
        return ls;
    }
    
    @RequestMapping(value = "api/getBuyPrice/{itemid}/{uomid}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public BuyPriceEntity getBuyPrice(@PathVariable String itemid, @PathVariable String uomid) {
        
        ItemEntity item = itemService.getItem(itemid);
        UomEntity uom = uomService.getUom(uomid);
        
        return buypriceService.getBuyPrice(item, uom);
    }
    
    @RequestMapping(value = "api/getBuyPrice/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public BuyPriceEntity getBuyPrice(@PathVariable String id) {
        return buypriceService.getBuyPrice(id);
    }

    @RequestMapping(value = "api/addBuyPrice", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public BuyPriceEntity addBuyPrice(@RequestBody BuyPriceEntity buyprice) {
        buyprice.setId(IdGenerator.generateId());
        buyprice.setLastmodified(Calendar.getInstance());
        
        buypriceService.addBuyPrice(buyprice);
        
        return buyprice;
    }

    @RequestMapping(value = "api/updateBuyPrice", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateBuyPrice(@RequestBody BuyPriceEntity buyprice) {
        buypriceService.updateBuyPrice(buyprice);
    }

    @RequestMapping(value = "api/deleteBuyPrice/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteBuyPrice(@PathVariable String id) {
        buypriceService.deleteBuyPrice(id);		
    }
}
