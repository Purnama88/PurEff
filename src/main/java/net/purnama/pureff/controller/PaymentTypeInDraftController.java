/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.PaymentTypeInDraftService;
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
public class PaymentTypeInDraftController {
    
    @Autowired
    PaymentTypeInDraftService paymenttypeindraftService;
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @RequestMapping(value = "api/getPaymentTypeInDraftList/{paymentid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentTypeInDraftEntity> getPaymentTypeInDraftList(@PathVariable String paymentid) {
        
        PaymentInDraftEntity paymentindraft = paymentindraftService.getPaymentInDraft(paymentid);
        
        List<PaymentTypeInDraftEntity> ls = paymenttypeindraftService.getPaymentTypeInDraftList(paymentindraft);
        return ls;
    }
}
