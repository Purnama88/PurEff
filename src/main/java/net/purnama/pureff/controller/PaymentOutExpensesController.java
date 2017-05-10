/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
import net.purnama.pureff.service.PaymentOutExpensesService;
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
public class PaymentOutExpensesController {
    @Autowired
    PaymentOutExpensesService paymentoutexpensesService;
    
    @RequestMapping(value = "api/getPaymentOutExpensesEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentOutExpensesEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        List<PaymentOutExpensesEntity> ls = paymentoutexpensesService.
                getPaymentOutExpensesEntityList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentOutExpensesEntity(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentid);
        
        ExpensesEntity expenses = new ExpensesEntity();
        expenses.setId(invoiceid);
            
        PaymentOutExpensesEntity piisde = paymentoutexpensesService.
                getPaymentOutExpensesEntity(paymentout,
                        expenses);
        
        return ResponseEntity.ok(piisde);
    }
}
