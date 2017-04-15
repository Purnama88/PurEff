/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentTypeInService;
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
public class PaymentTypeInController {
    
    @Autowired
    PaymentTypeInService paymenttypeinService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @RequestMapping(value = "api/getPaymentTypeInList/{paymentid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentTypeInEntity> getPaymentTypeInList(@PathVariable String paymentid) {
        
        PaymentInEntity paymentin = paymentinService.getPaymentIn(paymentid);
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPaymentTypeInList(paymentin);
        return ls;
    }
    
    @RequestMapping(value = "api/getPendingPaymentTypeInList/{type}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentTypeInEntity> getPendingPaymentTypeInList(@PathVariable int type) {
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPendingPaymentTypeInList(type);
        return ls;
    }
}
