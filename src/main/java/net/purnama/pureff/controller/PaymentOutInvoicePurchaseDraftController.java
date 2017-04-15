/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import net.purnama.pureff.service.PaymentOutDraftService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseDraftService;
import net.purnama.pureff.service.InvoicePurchaseService;
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
public class PaymentOutInvoicePurchaseDraftController {
    
    @Autowired
    PaymentOutInvoicePurchaseDraftService paymentoutinvoicepurchasedraftService;
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutInvoicePurchaseDraftEntity> getPaymentOutInvoicePurchaseDraftEntityList(@PathVariable String id) {
        
        List<PaymentOutInvoicePurchaseDraftEntity> ls = paymentoutinvoicepurchasedraftService.
                getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraftService.getPaymentOutDraft(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseDraftEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutInvoicePurchaseDraftEntity 
        getPaymentOutInvoicePurchaseDraftEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutInvoicePurchaseDraftEntity piisde = paymentoutinvoicepurchasedraftService.
                getPaymentOutInvoicePurchaseDraftEntity(paymentoutdraftService.getPaymentOutDraft(paymentid),
                        invoicepurchaseService.getInvoicePurchase(invoiceid));
        
        return piisde;
    }
    
    
}
