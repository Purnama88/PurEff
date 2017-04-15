/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.PaymentOutDraftService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseDraftService;
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
public class PaymentOutReturnPurchaseDraftController {
    
    @Autowired
    PaymentOutReturnPurchaseDraftService paymentoutreturnpurchasedraftService;
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutReturnPurchaseDraftEntity> getPaymentOutReturnPurchaseDraftEntityList(@PathVariable String id) {
        
        List<PaymentOutReturnPurchaseDraftEntity> ls = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraftService.getPaymentOutDraft(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraftEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutReturnPurchaseDraftEntity 
        getPaymentOutReturnPurchaseDraftEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutReturnPurchaseDraftEntity piisde = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntity(paymentoutdraftService.getPaymentOutDraft(paymentid),
                        returnpurchaseService.getReturnPurchase(invoiceid));
        
        return piisde;
    }
    
}
