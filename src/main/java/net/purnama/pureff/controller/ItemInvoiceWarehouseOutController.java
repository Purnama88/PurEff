/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
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
public class ItemInvoiceWarehouseOutController {
    @Autowired
    ItemInvoiceWarehouseOutService iteminvoicewarehouseoutService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseOutList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoiceWarehouseOutEntity> getItemInvoiceWarehouseOutList(@PathVariable String id) {
        InvoiceWarehouseOutEntity ad = invoicewarehouseoutService.getInvoiceWarehouseOut(id);
        
        return iteminvoicewarehouseoutService.getItemInvoiceWarehouseOutList(ad);
    }
}