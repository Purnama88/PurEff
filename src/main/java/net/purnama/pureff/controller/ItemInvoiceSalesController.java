/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.ItemInvoiceSalesService;
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
public class ItemInvoiceSalesController {
    @Autowired
    ItemInvoiceSalesService iteminvoicesalesService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getItemInvoiceSalesList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoiceSalesEntity> getItemInvoiceSalesList(@PathVariable String id) {
        InvoiceSalesEntity ad = invoicesalesService.getInvoiceSales(id);
        
        return iteminvoicesalesService.getItemInvoiceSalesList(ad);
    }
}
