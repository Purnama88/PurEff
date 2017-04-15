/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
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
public class InvoiceWarehouseOutController {
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoiceWarehouseOutEntity> getInvoiceWarehouseOutList() {
        
        List<InvoiceWarehouseOutEntity> ls = invoicewarehouseoutService.getInvoiceWarehouseOutList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseOut/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoiceWarehouseOutEntity getInvoiceWarehouseOut(@PathVariable String id) {
        return invoicewarehouseoutService.getInvoiceWarehouseOut(id);
    }

    @RequestMapping(value = "api/addInvoiceWarehouseOut", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoiceWarehouseOutEntity addInvoiceWarehouseOut(@RequestBody InvoiceWarehouseOutEntity invoicewarehouseout) {
        invoicewarehouseout.setId(IdGenerator.generateId());
        invoicewarehouseout.setLastmodified(Calendar.getInstance());
        
        invoicewarehouseoutService.addInvoiceWarehouseOut(invoicewarehouseout);
        
        return invoicewarehouseout;
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoiceWarehouseOut(@RequestBody InvoiceWarehouseOutEntity invoicewarehouseout) {
        invoicewarehouseoutService.updateInvoiceWarehouseOut(invoicewarehouseout);
    }
}
