/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
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
public class ItemInvoiceWarehouseOutController {
    @Autowired
    ItemInvoiceWarehouseOutService iteminvoicewarehouseoutService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseOutList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceWarehouseOutList(@RequestParam(value="id") String id) {
        InvoiceWarehouseOutEntity ad = invoicewarehouseoutService.getInvoiceWarehouseOut(id);
        
        return ResponseEntity.ok(iteminvoicewarehouseoutService.getItemInvoiceWarehouseOutList(ad));
    }
}