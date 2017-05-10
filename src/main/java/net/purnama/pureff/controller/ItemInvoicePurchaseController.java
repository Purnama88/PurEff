/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.ItemInvoicePurchaseService;
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
public class ItemInvoicePurchaseController {
    @Autowired
    ItemInvoicePurchaseService iteminvoicepurchaseService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @RequestMapping(value = "api/getItemInvoicePurchaseList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoicePurchaseList(@RequestParam(value="id") String id) {
        InvoicePurchaseEntity ad = invoicepurchaseService.getInvoicePurchase(id);
        
        return ResponseEntity.ok(iteminvoicepurchaseService.getItemInvoicePurchaseList(ad));
    }
}
