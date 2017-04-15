/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.service.InvoiceWarehouseInDraftService;
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
public class InvoiceWarehouseInDraftController {
    
    @Autowired
    InvoiceWarehouseInDraftService invoicewarehouseindraftService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoiceWarehouseInDraftEntity> getInvoiceWarehouseInDraftList() {
        
        List<InvoiceWarehouseInDraftEntity> ls = invoicewarehouseindraftService.getInvoiceWarehouseInDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseInDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoiceWarehouseInDraftEntity getInvoiceWarehouseInDraft(@PathVariable String id) {
        return invoicewarehouseindraftService.getInvoiceWarehouseInDraft(id);
    }

    @RequestMapping(value = "api/addInvoiceWarehouseInDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoiceWarehouseInDraftEntity addInvoiceWarehouseInDraft(@RequestBody InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        invoicewarehouseindraft.setId(IdGenerator.generateId());
        invoicewarehouseindraft.setLastmodified(Calendar.getInstance());
        
        invoicewarehouseindraftService.addInvoiceWarehouseInDraft(invoicewarehouseindraft);
        
        return invoicewarehouseindraft;
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseInDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoiceWarehouseInDraft(@RequestBody InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        invoicewarehouseindraftService.updateInvoiceWarehouseInDraft(invoicewarehouseindraft);
    }

    @RequestMapping(value = "api/deleteInvoiceWarehouseInDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteInvoiceWarehouseInDraft(@PathVariable String id) {
        invoicewarehouseindraftService.deleteInvoiceWarehouseInDraft(id);		
    }
}
