/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.ArrayList;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.PaymentOutExpensesDraftService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PaymentOutExpensesDraftController {
    @Autowired
    PaymentOutExpensesDraftService paymentoutexpensesdraftService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getSelectedPaymentOutExpensesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getSelectedPaymentOutExpensesDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentOutExpensesDraftEntity> ls = paymentoutexpensesdraftService.
                getPaymentOutExpensesDraftEntityList(paymentoutdraft, partner, currency);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getPaymentOutExpensesDraftList(
            @RequestParam(value="paymentid")String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<ExpensesEntity> expenseslist = expensesService.
                getUnpaidExpensesList(partner, currency);
        
        List<PaymentOutExpensesDraftEntity> retpaymentoutexpensesdraftlist =
                new ArrayList<>();
        
        for(ExpensesEntity invoice : expenseslist){
            
            PaymentOutExpensesDraftEntity piisd = paymentoutexpensesdraftService.
                        getPaymentOutExpensesDraftEntity(paymentoutdraft, invoice);

            if(piisd == null){
                PaymentOutExpensesDraftEntity newpiisd = new PaymentOutExpensesDraftEntity();
                newpiisd.setExpenses(invoice);
                newpiisd.setPaymentoutdraft(paymentoutdraft);
                newpiisd.setAmount(invoice.getRemaining());
                retpaymentoutexpensesdraftlist.add(newpiisd);
            }
            else{
                if(invoice.getRemaining() - piisd.getAmount() > 0){
                    PaymentOutExpensesDraftEntity newpiisd = new PaymentOutExpensesDraftEntity();
                    newpiisd.setExpenses(invoice);
                    newpiisd.setPaymentoutdraft(paymentoutdraft);
                    newpiisd.setAmount(invoice.getRemaining() - piisd.getAmount());

                    retpaymentoutexpensesdraftlist.add(newpiisd);
                }
            }
        }
        
        return ResponseEntity.ok(retpaymentoutexpensesdraftlist);
    }
    
    @RequestMapping(value = "api/getPaymentOutExpensesDraft",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentOutExpensesDraft(
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
    
    @RequestMapping(value = "api/savePaymentOutExpensesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> savePaymentOutExpensesDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestBody List<PaymentOutExpensesDraftEntity> paymentoutexpensesdraftlist) {
        
        PaymentOutDraftEntity pode = new PaymentOutDraftEntity();
        pode.setId(paymentid);
        
        for(PaymentOutExpensesDraftEntity piisdetemp : 
                paymentoutexpensesdraftService.getPaymentOutExpensesDraftEntityList(pode)){
            paymentoutexpensesdraftService.deletePaymentOutExpensesDraft(piisdetemp.getId());
        }

        for(PaymentOutExpensesDraftEntity piisdetemp : paymentoutexpensesdraftlist){
                piisdetemp.setId(IdGenerator.generateId());
                paymentoutexpensesdraftService.addPaymentOutExpensesDraft(piisdetemp);
        }
        
        return ResponseEntity.ok("");
    }
}
