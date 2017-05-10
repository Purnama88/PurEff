/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.PaymentTypeOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class PaymentTypeOutController {
    
    @Autowired
    PaymentTypeOutService paymenttypeoutService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentTypeOutList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = paymentoutService.getPaymentOut(paymentid);
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPaymentTypeOutList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPendingPaymentTypeOutList/{type}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentTypeOutEntity> getPendingPaymentTypeOutList(@PathVariable int type) {
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPendingPaymentTypeOutList(type);
        return ls;
    }
}
