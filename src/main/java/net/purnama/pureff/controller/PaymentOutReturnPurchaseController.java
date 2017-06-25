/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutReturnPurchaseEntity;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
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
public class PaymentOutReturnPurchaseController {
    
    @Autowired
    PaymentOutReturnPurchaseService paymentoutreturnpurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentOutReturnPurchaseList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        List<PaymentOutReturnPurchaseEntity> ls = paymentoutreturnpurchaseService.
                getPaymentOutReturnPurchaseEntityList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchase",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentOutReturnPurchase(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
            
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        ReturnPurchaseEntity returnpurchase = new ReturnPurchaseEntity();
        returnpurchase.setId(invoiceid);
            
        PaymentOutReturnPurchaseEntity piisde = paymentoutreturnpurchaseService.
                getPaymentOutReturnPurchaseEntity(paymentout,
                        returnpurchase);
        
        return ResponseEntity.ok(piisde);
    }
    
}
