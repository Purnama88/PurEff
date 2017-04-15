/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class PaymentOutController {
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @RequestMapping(value = "api/getPaymentOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutEntity> getPaymentOutList() {
        
        List<PaymentOutEntity> ls = paymentoutService.getPaymentOutList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOut/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PaymentOutEntity getPaymentOut(@PathVariable String id) {
        return paymentoutService.getPaymentOut(id);
    }

    @RequestMapping(value = "api/addPaymentOut", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PaymentOutEntity addPaymentOut(@RequestBody PaymentOutEntity paymentout) {
        paymentout.setId(IdGenerator.generateId());
        paymentout.setLastmodified(Calendar.getInstance());
        
        paymentoutService.addPaymentOut(paymentout);
        
        return paymentout;
    }

    @RequestMapping(value = "api/updatePaymentOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updatePaymentOut(@RequestBody PaymentOutEntity paymentout) {
        paymentoutService.updatePaymentOut(paymentout);
    }
}
