/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.BuyPriceEntity;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.service.BuyPriceService;
import net.purnama.pureff.service.ItemService;
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
public class BuyPriceController {
    
    @Autowired
    BuyPriceService buypriceService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getBuyPriceList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemid"})
    public ResponseEntity<?> getBuyPriceList(@RequestParam(value="itemid") String itemid) {
        
        ItemEntity item = itemService.getItem(itemid);
        
        List<BuyPriceEntity> ls = buypriceService.getBuyPriceList(item);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getBuyPrice", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"itemid", "uomid"})
    public ResponseEntity<?> getBuyPrice(
        @RequestParam(value="itemid") String itemid,
            @RequestParam(value="uomid") String uomid) {
        
        ItemEntity item = itemService.getItem(itemid);
        UomEntity uom = uomService.getUom(uomid);
        
        return ResponseEntity.ok(buypriceService.getBuyPrice(item, uom));
    }
    
    @RequestMapping(value = "api/updateBuyPrice", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateBuyPrice(@RequestBody BuyPriceEntity buyprice) {
        
        buypriceService.updateBuyPrice(buyprice);
        
        return ResponseEntity.ok(buyprice);
    }
}
