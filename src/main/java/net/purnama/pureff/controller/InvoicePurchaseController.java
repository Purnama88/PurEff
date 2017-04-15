/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.service.InvoicePurchaseService;
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
public class InvoicePurchaseController {
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @RequestMapping(value = "api/getInvoicePurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoicePurchaseEntity> getInvoicePurchaseList() {
        
        List<InvoicePurchaseEntity> ls = invoicepurchaseService.getInvoicePurchaseList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoicePurchase/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoicePurchaseEntity getInvoicePurchase(@PathVariable String id) {
        return invoicepurchaseService.getInvoicePurchase(id);
    }

    @RequestMapping(value = "api/addInvoicePurchase", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoicePurchaseEntity addInvoicePurchase(@RequestBody InvoicePurchaseEntity invoicepurchase) {
        invoicepurchase.setId(IdGenerator.generateId());
        invoicepurchase.setLastmodified(Calendar.getInstance());
        
        invoicepurchaseService.addInvoicePurchase(invoicepurchase);
        
        return invoicepurchase;
    }

    @RequestMapping(value = "api/updateInvoicePurchase", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoicePurchase(@RequestBody InvoicePurchaseEntity invoicepurchase) {
        invoicepurchaseService.updateInvoicePurchase(invoicepurchase);
    }
}
