/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.service.PaymentInDraftService;
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
public class PaymentInDraftController {
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @RequestMapping(value = "api/getPaymentInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInDraftEntity> getPaymentInDraftList() {
        
        List<PaymentInDraftEntity> ls = paymentindraftService.getPaymentInDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PaymentInDraftEntity getPaymentInDraft(@PathVariable String id) {
        return paymentindraftService.getPaymentInDraft(id);
    }

    @RequestMapping(value = "api/addPaymentInDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PaymentInDraftEntity addPaymentInDraft(@RequestBody PaymentInDraftEntity paymentindraft) {
        paymentindraft.setId(IdGenerator.generateId());
        paymentindraft.setLastmodified(Calendar.getInstance());
        
        paymentindraftService.addPaymentInDraft(paymentindraft);
        
        return paymentindraft;
    }

    @RequestMapping(value = "api/updatePaymentInDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updatePaymentInDraft(@RequestBody PaymentInDraftEntity paymentindraft) {
        paymentindraftService.updatePaymentInDraft(paymentindraft);
    }

    @RequestMapping(value = "api/deletePaymentInDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deletePaymentInDraft(@PathVariable String id) {
        paymentindraftService.deletePaymentInDraft(id);		
    }
}
