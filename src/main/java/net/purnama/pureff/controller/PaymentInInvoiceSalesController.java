/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
import net.purnama.pureff.service.PaymentInInvoiceSalesService;
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
public class PaymentInInvoiceSalesController {
    
    @Autowired
    PaymentInInvoiceSalesService paymentininvoicesalesService;
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(paymentid);
        
        List<PaymentInInvoiceSalesEntity> ls = paymentininvoicesalesService.
                getPaymentInInvoiceSalesEntityList(paymentin);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(paymentid);
        
        InvoiceSalesEntity invoicesales = new InvoiceSalesEntity();
        invoicesales.setId(invoiceid);
        
        PaymentInInvoiceSalesEntity piisde = paymentininvoicesalesService.
                getPaymentInInvoiceSalesEntity(paymentin,
                        invoicesales);
        
        return ResponseEntity.ok(piisde);
    }
}
