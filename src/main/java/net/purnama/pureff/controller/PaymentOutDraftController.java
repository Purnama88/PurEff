/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.service.PaymentOutDraftService;
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
public class PaymentOutDraftController {
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @RequestMapping(value = "api/getPaymentOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutDraftEntity> getPaymentOutDraftList() {
        
        List<PaymentOutDraftEntity> ls = paymentoutdraftService.getPaymentOutDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PaymentOutDraftEntity getPaymentOutDraft(@PathVariable String id) {
        return paymentoutdraftService.getPaymentOutDraft(id);
    }

    @RequestMapping(value = "api/addPaymentOutDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PaymentOutDraftEntity addPaymentOutDraft(@RequestBody PaymentOutDraftEntity paymentoutdraft) {
        paymentoutdraft.setId(IdGenerator.generateId());
        paymentoutdraft.setLastmodified(Calendar.getInstance());
        
        paymentoutdraftService.addPaymentOutDraft(paymentoutdraft);
        
        return paymentoutdraft;
    }

    @RequestMapping(value = "api/updatePaymentOutDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updatePaymentOutDraft(@RequestBody PaymentOutDraftEntity paymentoutdraft) {
        paymentoutdraftService.updatePaymentOutDraft(paymentoutdraft);
    }

    @RequestMapping(value = "api/deletePaymentOutDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deletePaymentOutDraft(@PathVariable String id) {
        paymentoutdraftService.deletePaymentOutDraft(id);		
    }
}

