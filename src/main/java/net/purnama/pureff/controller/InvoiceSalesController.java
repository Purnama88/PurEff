/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.service.InvoiceSalesService;
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
public class InvoiceSalesController {
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getInvoiceSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<InvoiceSalesEntity> getInvoiceSalesList() {
        
        List<InvoiceSalesEntity> ls = invoicesalesService.getInvoiceSalesList();
        return ls;
    }
    
    @RequestMapping(value = "api/getInvoiceSales/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public InvoiceSalesEntity getInvoiceSales(@PathVariable String id) {
        return invoicesalesService.getInvoiceSales(id);
    }

    @RequestMapping(value = "api/addInvoiceSales", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public InvoiceSalesEntity addInvoiceSales(@RequestBody InvoiceSalesEntity invoicesales) {
        invoicesales.setId(IdGenerator.generateId());
        invoicesales.setLastmodified(Calendar.getInstance());
        
        invoicesalesService.addInvoiceSales(invoicesales);
        
        return invoicesales;
    }

    @RequestMapping(value = "api/updateInvoiceSales", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateInvoiceSales(@RequestBody InvoiceSalesEntity invoicesales) {
        invoicesalesService.updateInvoiceSales(invoicesales);
    }
}
