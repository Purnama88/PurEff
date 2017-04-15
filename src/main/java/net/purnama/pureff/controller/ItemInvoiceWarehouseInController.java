/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;
import net.purnama.pureff.service.InvoiceWarehouseInService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInService;
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
public class ItemInvoiceWarehouseInController {
    @Autowired
    ItemInvoiceWarehouseInService iteminvoicewarehouseinService;
    
    @Autowired
    InvoiceWarehouseInService invoicewarehouseinService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseInList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoiceWarehouseInEntity> getItemInvoiceWarehouseInList(@PathVariable String id) {
        InvoiceWarehouseInEntity ad = invoicewarehouseinService.getInvoiceWarehouseIn(id);
        
        return iteminvoicewarehouseinService.getItemInvoiceWarehouseInList(ad);
    }
}