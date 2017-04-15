/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.service.PaymentInService;
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
public class PaymentInController {
    
    @Autowired
    PaymentInService paymentinService;
    
    @RequestMapping(value = "api/getPaymentInList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInEntity> getPaymentInList() {
        
        List<PaymentInEntity> ls = paymentinService.getPaymentInList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentIn/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PaymentInEntity getPaymentIn(@PathVariable String id) {
        return paymentinService.getPaymentIn(id);
    }

    @RequestMapping(value = "api/addPaymentIn", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PaymentInEntity addPaymentIn(@RequestBody PaymentInEntity paymentin) {
        paymentin.setId(IdGenerator.generateId());
        paymentin.setLastmodified(Calendar.getInstance());
        
        paymentinService.addPaymentIn(paymentin);
        
        return paymentin;
    }

    @RequestMapping(value = "api/updatePaymentIn", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updatePaymentIn(@RequestBody PaymentInEntity paymentin) {
        paymentinService.updatePaymentIn(paymentin);
    }
}
