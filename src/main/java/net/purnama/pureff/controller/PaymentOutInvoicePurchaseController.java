/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
import net.purnama.pureff.service.PaymentOutService;
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
public class PaymentOutInvoicePurchaseController {
    
    @Autowired
    PaymentOutInvoicePurchaseService paymentoutinvoicepurchaseService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseEntityListByPaymentOut", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutInvoicePurchaseEntity> getPaymentOutInvoicePurchaseEntityListByPaymentOut(@PathVariable String id) {
        
        List<PaymentOutInvoicePurchaseEntity> ls = paymentoutinvoicepurchaseService.
                getPaymentOutInvoicePurchaseEntityList(paymentoutService.getPaymentOut(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseEntityListByInvoicePurchase", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutInvoicePurchaseEntity> getPaymentOutInvoicePurchaseEntityListByInvoicePurchase(@PathVariable String id) {
        
        List<PaymentOutInvoicePurchaseEntity> ls = paymentoutinvoicepurchaseService.
                getPaymentOutInvoicePurchaseEntityList(invoicepurchaseService.getInvoicePurchase(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutInvoicePurchaseEntity 
        getPaymentOutInvoicePurchaseEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutInvoicePurchaseEntity piisde = paymentoutinvoicepurchaseService.
                getPaymentOutInvoicePurchaseEntity(paymentoutService.getPaymentOut(paymentid),
                        invoicepurchaseService.getInvoicePurchase(invoiceid));
        
        return piisde;
    }
    
}
