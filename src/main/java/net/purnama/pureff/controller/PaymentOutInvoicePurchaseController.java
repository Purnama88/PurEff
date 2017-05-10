/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class PaymentOutInvoicePurchaseController {
    
    @Autowired
    PaymentOutInvoicePurchaseService paymentoutinvoicepurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentOutInvoicePurchaseEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        List<PaymentOutInvoicePurchaseEntity> ls = paymentoutinvoicepurchaseService.
                getPaymentOutInvoicePurchaseEntityList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentOutInvoicePurchaseEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        InvoicePurchaseEntity invoicepurchase = new InvoicePurchaseEntity();
        invoicepurchase.setId(invoiceid);
        
        PaymentOutInvoicePurchaseEntity piisde = paymentoutinvoicepurchaseService.
                getPaymentOutInvoicePurchaseEntity(paymentout,
                        invoicepurchase);
        
        return ResponseEntity.ok(piisde);
    }
    
    
}
