/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.pureff.service.InvoicePurchaseDraftService;
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
public class InvoicePurchaseDraftController {
    
    @Autowired
    InvoicePurchaseDraftService invoicepurchasedraftService;
    
    @RequestMapping(value = "api/getInvoicePurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoicePurchaseDraftEntity> getInvoicePurchaseDraftList() {
        
        List<InvoicePurchaseDraftEntity> ls = invoicepurchasedraftService.getInvoicePurchaseDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoicePurchaseDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoicePurchaseDraftEntity getInvoicePurchaseDraft(@PathVariable String id) {
        return invoicepurchasedraftService.getInvoicePurchaseDraft(id);
    }

    @RequestMapping(value = "api/addInvoicePurchaseDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoicePurchaseDraftEntity addInvoicePurchaseDraft(@RequestBody InvoicePurchaseDraftEntity invoicepurchasedraft) {
        invoicepurchasedraft.setId(IdGenerator.generateId());
        invoicepurchasedraft.setLastmodified(Calendar.getInstance());
        
        invoicepurchasedraftService.addInvoicePurchaseDraft(invoicepurchasedraft);
        
        return invoicepurchasedraft;
    }

    @RequestMapping(value = "api/updateInvoicePurchaseDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoicePurchaseDraft(@RequestBody InvoicePurchaseDraftEntity invoicepurchasedraft) {
        invoicepurchasedraftService.updateInvoicePurchaseDraft(invoicepurchasedraft);
    }

    @RequestMapping(value = "api/deleteInvoicePurchaseDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteInvoicePurchaseDraft(@PathVariable String id) {
        invoicepurchasedraftService.deleteInvoicePurchaseDraft(id);		
    }
}
