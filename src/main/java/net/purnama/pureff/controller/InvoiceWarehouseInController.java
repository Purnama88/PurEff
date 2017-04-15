/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.service.InvoiceWarehouseInService;
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
public class InvoiceWarehouseInController {
    
    @Autowired
    InvoiceWarehouseInService invoicewarehouseinService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseInList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoiceWarehouseInEntity> getInvoiceWarehouseInList() {
        
        List<InvoiceWarehouseInEntity> ls = invoicewarehouseinService.getInvoiceWarehouseInList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseIn/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoiceWarehouseInEntity getInvoiceWarehouseIn(@PathVariable String id) {
        return invoicewarehouseinService.getInvoiceWarehouseIn(id);
    }

    @RequestMapping(value = "api/addInvoiceWarehouseIn", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoiceWarehouseInEntity addInvoiceWarehouseIn(@RequestBody InvoiceWarehouseInEntity invoicewarehousein) {
        invoicewarehousein.setId(IdGenerator.generateId());
        invoicewarehousein.setLastmodified(Calendar.getInstance());
        
        invoicewarehouseinService.addInvoiceWarehouseIn(invoicewarehousein);
        
        return invoicewarehousein;
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseIn", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoiceWarehouseIn(@RequestBody InvoiceWarehouseInEntity invoicewarehousein) {
        invoicewarehouseinService.updateInvoiceWarehouseIn(invoicewarehousein);
    }
}

