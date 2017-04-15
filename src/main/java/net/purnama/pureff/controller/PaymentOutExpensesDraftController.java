/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.PaymentOutDraftService;
import net.purnama.pureff.service.PaymentOutExpensesDraftService;
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
public class PaymentOutExpensesDraftController {
    @Autowired
    PaymentOutExpensesDraftService paymentoutexpensesdraftService;
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutExpensesDraftEntity> getPaymentOutExpensesDraftEntityList(@PathVariable String id) {
        
        List<PaymentOutExpensesDraftEntity> ls = paymentoutexpensesdraftService.
                getPaymentOutExpensesDraftEntityList(paymentoutdraftService.getPaymentOutDraft(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraftEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutExpensesDraftEntity 
        getPaymentOutExpensesDraftEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutExpensesDraftEntity piisde = paymentoutexpensesdraftService.
                getPaymentOutExpensesDraftEntity(paymentoutdraftService.getPaymentOutDraft(paymentid),
                        expensesService.getExpenses(invoiceid));
        
        return piisde;
    }
}
