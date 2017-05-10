/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.pureff.service.PaymentInInvoiceSalesDraftService;
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
public class PaymentInInvoiceSalesDraftController {
    
    @Autowired
    PaymentInInvoiceSalesDraftService paymentininvoicesalesdraftService;
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesDraftEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        List<PaymentInInvoiceSalesDraftEntity> ls = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraftEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesDraftEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        InvoiceSalesEntity invoicesales = new InvoiceSalesEntity();
        invoicesales.setId(invoiceid);
        
        PaymentInInvoiceSalesDraftEntity piisde = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntity(paymentindraft,
                        invoicesales);
        
        return ResponseEntity.ok(piisde);
    }
}
