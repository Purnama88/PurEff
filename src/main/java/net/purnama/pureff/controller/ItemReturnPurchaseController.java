/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.ItemReturnPurchaseService;
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
public class ItemReturnPurchaseController {
    @Autowired
    ItemReturnPurchaseService itemreturnpurchaseService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getItemReturnPurchaseList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemReturnPurchaseList(@RequestParam(value="id") String id) {
        ReturnPurchaseEntity ad = returnpurchaseService.getReturnPurchase(id);
        
        return ResponseEntity.ok(itemreturnpurchaseService.getItemReturnPurchaseList(ad));
    }
}
