/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.ItemInvoiceSalesService;
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
public class ItemInvoiceSalesController {
    @Autowired
    ItemInvoiceSalesService iteminvoicesalesService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getItemInvoiceSalesList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceSalesList(@RequestParam(value="id") String id) {
        InvoiceSalesEntity ad = invoicesalesService.getInvoiceSales(id);
        
        return ResponseEntity.ok(iteminvoicesalesService.getItemInvoiceSalesList(ad));
    }
}
