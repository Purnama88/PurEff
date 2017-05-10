/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.pureff.service.PaymentOutReturnPurchaseDraftService;
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
public class PaymentOutReturnPurchaseDraftController {
    
    @Autowired
    PaymentOutReturnPurchaseDraftService paymentoutreturnpurchasedraftService;
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentOutReturnPurchaseDraftEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        List<PaymentOutReturnPurchaseDraftEntity> ls = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraftEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> 
        getPaymentOutReturnPurchaseDraftEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
            
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        ReturnPurchaseEntity returnpurchase = new ReturnPurchaseEntity();
        returnpurchase.setId(invoiceid);
            
        PaymentOutReturnPurchaseDraftEntity piisde = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntity(paymentoutdraft,
                        returnpurchase);
        
        return ResponseEntity.ok(piisde);
    }
    
}
