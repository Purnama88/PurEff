/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeOutDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentOutDraftService;
import net.purnama.pureff.service.PaymentTypeOutDraftService;
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
public class PaymentTypeOutDraftController {
    
    @Autowired
    PaymentTypeOutDraftService paymenttypeoutdraftService;
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @RequestMapping(value = "api/getPaymentTypeOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentTypeOutDraftList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        List<PaymentTypeOutDraftEntity> ls = paymenttypeoutdraftService.getPaymentTypeOutDraftList(paymentoutdraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/savePaymentTypeOutDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentTypeOutDraftList(
            HttpServletRequest httpRequest,
            @RequestBody List<PaymentTypeOutDraftEntity> paymenttypeoutdraftlist) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        for(PaymentTypeOutDraftEntity paymenttypeoutdraft : paymenttypeoutdraftlist){
            paymenttypeoutdraft.setLastmodifiedby(temp);
            if(paymenttypeoutdraft.getId() != null){
                paymenttypeoutdraftService.updatePaymentTypeOutDraft(paymenttypeoutdraft);
            }
            else{
                paymenttypeoutdraft.setId(IdGenerator.generateId());
                paymenttypeoutdraftService.addPaymentTypeOutDraft(paymenttypeoutdraft);
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deletePaymentTypeOutDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deletePaymentTypeOutDraftList(
            @RequestBody List<PaymentTypeOutDraftEntity> paymenttypeoutdraftlist){
        
        for(PaymentTypeOutDraftEntity paymenttypeoutdraft : paymenttypeoutdraftlist){
            if(paymenttypeoutdraft.getId() != null){
                paymenttypeoutdraftService.deletePaymentTypeOutDraft(paymenttypeoutdraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}
