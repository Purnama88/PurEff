/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
import net.purnama.pureff.service.PaymentOutExpensesService;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.ExpensesService;
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
public class PaymentOutExpensesController {
    
    @Autowired
    PaymentOutExpensesService paymentoutexpensesService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getPaymentOutExpensesEntityListByPaymentOut", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutExpensesEntity> getPaymentOutExpensesEntityListByPaymentOut(@PathVariable String id) {
        
        List<PaymentOutExpensesEntity> ls = paymentoutexpensesService.
                getPaymentOutExpensesEntityList(paymentoutService.getPaymentOut(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesEntityListByExpenses", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentOutExpensesEntity> getPaymentOutExpensesEntityListByExpenses(@PathVariable String id) {
        
        List<PaymentOutExpensesEntity> ls = paymentoutexpensesService.
                getPaymentOutExpensesEntityList(expensesService.getExpenses(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentOutExpensesEntity 
        getPaymentOutExpensesEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentOutExpensesEntity piisde = paymentoutexpensesService.
                getPaymentOutExpensesEntity(paymentoutService.getPaymentOut(paymentid),
                        expensesService.getExpenses(invoiceid));
        
        return piisde;
    }
    
}
