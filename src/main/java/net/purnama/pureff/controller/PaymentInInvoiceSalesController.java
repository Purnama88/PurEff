/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentInInvoiceSalesService;
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
public class PaymentInInvoiceSalesController {
    
    @Autowired
    PaymentInInvoiceSalesService paymentininvoicesalesService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesEntityListByPaymentIn", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInInvoiceSalesEntity> getPaymentInInvoiceSalesEntityListByPaymentIn(@PathVariable String id) {
        
        List<PaymentInInvoiceSalesEntity> ls = paymentininvoicesalesService.
                getPaymentInInvoiceSalesEntityList(paymentinService.getPaymentIn(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesEntityListByInvoiceSales", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInInvoiceSalesEntity> getPaymentInInvoiceSalesEntityListByInvoiceSales(@PathVariable String id) {
        
        List<PaymentInInvoiceSalesEntity> ls = paymentininvoicesalesService.
                getPaymentInInvoiceSalesEntityList(invoicesalesService.getInvoiceSales(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentInInvoiceSalesEntity 
        getPaymentInInvoiceSalesEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentInInvoiceSalesEntity piisde = paymentininvoicesalesService.
                getPaymentInInvoiceSalesEntity(paymentinService.getPaymentIn(paymentid),
                        invoicesalesService.getInvoiceSales(invoiceid));
        
        return piisde;
    }
    
}
