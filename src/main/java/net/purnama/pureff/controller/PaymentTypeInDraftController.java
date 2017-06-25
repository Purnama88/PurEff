/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.PaymentTypeInDraftService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class PaymentTypeInDraftController {
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @Autowired
    PaymentTypeInDraftService paymenttypeindraftService;
    
    @RequestMapping(value = "api/getPaymentTypeInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentTypeInDraftList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        List<PaymentTypeInDraftEntity> ls = paymenttypeindraftService.getPaymentTypeInDraftList(paymentindraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/savePaymentTypeInDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentTypeInDraftList(
            HttpServletRequest httpRequest,
            @RequestBody List<PaymentTypeInDraftEntity> paymenttypeindraftlist) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        for(PaymentTypeInDraftEntity paymenttypeindraft : paymenttypeindraftlist){
            paymenttypeindraft.setLastmodifiedby(temp);
            if(paymenttypeindraft.getId() != null){
                paymenttypeindraftService.updatePaymentTypeInDraft(paymenttypeindraft);
            }
            else{
                paymenttypeindraft.setId(IdGenerator.generateId());
                paymenttypeindraftService.addPaymentTypeInDraft(paymenttypeindraft);
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deletePaymentTypeInDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deletePaymentTypeInDraftList(
            @RequestBody List<PaymentTypeInDraftEntity> paymenttypeindraftlist){
        
        for(PaymentTypeInDraftEntity paymenttypeindraft : paymenttypeindraftlist){
            if(paymenttypeindraft.getId() != null){
                paymenttypeindraftService.deletePaymentTypeInDraft(paymenttypeindraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}
