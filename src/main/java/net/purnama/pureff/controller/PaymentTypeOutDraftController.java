/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeOutDraftEntity;
import net.purnama.pureff.service.PaymentTypeOutDraftService;
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
public class PaymentTypeOutDraftController {
    
    @Autowired
    PaymentTypeOutDraftService paymenttypeoutdraftService;
    
    
    @RequestMapping(value = "api/getPaymentTypeOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentTypeOutDraftList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        List<PaymentTypeOutDraftEntity> ls = paymenttypeoutdraftService.getPaymentTypeOutDraftList(paymentoutdraft);
        return ResponseEntity.ok(ls);
    }
}
