/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.pureff.service.PaymentOutExpensesDraftService;
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
public class PaymentOutExpensesDraftController {
    @Autowired
    PaymentOutExpensesDraftService paymentoutexpensesdraftService;
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentOutExpensesDraftEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        List<PaymentOutExpensesDraftEntity> ls = paymentoutexpensesdraftService.
                getPaymentOutExpensesDraftEntityList(paymentoutdraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraftEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentOutExpensesDraftEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        ExpensesEntity expenses = new ExpensesEntity();
        expenses.setId(invoiceid);
            
        PaymentOutExpensesDraftEntity piisde = paymentoutexpensesdraftService.
                getPaymentOutExpensesDraftEntity(paymentoutdraft,
                        expenses);
        
        return ResponseEntity.ok(piisde);
    }
}
