/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutReturnPurchaseEntity;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
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
public class PaymentOutReturnPurchaseController {
    @Autowired
    PaymentOutReturnPurchaseService paymentoutreturnpurchaseService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseEntityListByPaymentOut", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutReturnPurchaseEntity> getPaymentOutReturnPurchaseEntityListByPaymentOut(@PathVariable String id) {
        
        List<PaymentOutReturnPurchaseEntity> ls = paymentoutreturnpurchaseService.
                getPaymentOutReturnPurchaseEntityList(paymentoutService.getPaymentOut(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseEntityListByReturnPurchase", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutReturnPurchaseEntity> getPaymentOutReturnPurchaseEntityListByReturnPurchase(@PathVariable String id) {
        
        List<PaymentOutReturnPurchaseEntity> ls = paymentoutreturnpurchaseService.
                getPaymentOutReturnPurchaseEntityList(returnpurchaseService.getReturnPurchase(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutReturnPurchaseEntity 
        getPaymentOutReturnPurchaseEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutReturnPurchaseEntity piisde = paymentoutreturnpurchaseService.
                getPaymentOutReturnPurchaseEntity(paymentoutService.getPaymentOut(paymentid),
                        returnpurchaseService.getReturnPurchase(invoiceid));
        
        return piisde;
    }
}
