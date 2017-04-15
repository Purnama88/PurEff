/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutDraftService;
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
public class InvoiceWarehouseOutDraftController {
    
    @Autowired
    InvoiceWarehouseOutDraftService invoicewarehouseoutdraftService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoiceWarehouseOutDraftEntity> getInvoiceWarehouseOutDraftList() {
        
        List<InvoiceWarehouseOutDraftEntity> ls = invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoiceWarehouseOutDraftEntity getInvoiceWarehouseOutDraft(@PathVariable String id) {
        return invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraft(id);
    }

    @RequestMapping(value = "api/addInvoiceWarehouseOutDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoiceWarehouseOutDraftEntity addInvoiceWarehouseOutDraft(@RequestBody InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        invoicewarehouseoutdraft.setId(IdGenerator.generateId());
        invoicewarehouseoutdraft.setLastmodified(Calendar.getInstance());
        
        invoicewarehouseoutdraftService.addInvoiceWarehouseOutDraft(invoicewarehouseoutdraft);
        
        return invoicewarehouseoutdraft;
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseOutDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoiceWarehouseOutDraft(@RequestBody InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        invoicewarehouseoutdraftService.updateInvoiceWarehouseOutDraft(invoicewarehouseoutdraft);
    }

    @RequestMapping(value = "api/deleteInvoiceWarehouseOutDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteInvoiceWarehouseOutDraft(@PathVariable String id) {
        invoicewarehouseoutdraftService.deleteInvoiceWarehouseOutDraft(id);		
    }
}
