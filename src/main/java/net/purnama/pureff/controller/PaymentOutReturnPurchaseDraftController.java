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
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.pureff.service.PaymentOutReturnPurchaseDraftService;
import net.purnama.pureff.service.ReturnPurchaseService;
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
public class PaymentOutReturnPurchaseDraftController {
    
    @Autowired
    PaymentOutReturnPurchaseDraftService paymentoutreturnpurchasedraftService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getSelectedPaymentOutReturnPurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getSelectedPaymentOutReturnPurchaseDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentOutReturnPurchaseDraftEntity> ls = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getPaymentOutReturnPurchaseDraftList(
            @RequestParam(value="paymentid")String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<ReturnPurchaseEntity> returnpurchaselist = returnpurchaseService.
                getUnpaidReturnPurchaseList(partner, currency);
        
        List<PaymentOutReturnPurchaseDraftEntity> retpaymentoutreturnpurchasedraftlist =
                new ArrayList<>();
        
        for(ReturnPurchaseEntity invoice : returnpurchaselist){
            
            PaymentOutReturnPurchaseDraftEntity piisd = paymentoutreturnpurchasedraftService.
                        getPaymentOutReturnPurchaseDraftEntity(paymentoutdraft, invoice);

            if(piisd == null){
                PaymentOutReturnPurchaseDraftEntity newpiisd = new PaymentOutReturnPurchaseDraftEntity();
                newpiisd.setReturnpurchase(invoice);
                newpiisd.setPaymentoutdraft(paymentoutdraft);
                newpiisd.setAmount(invoice.getRemaining());
                retpaymentoutreturnpurchasedraftlist.add(newpiisd);
            }
            else{
                if(invoice.getRemaining() - piisd.getAmount() > 0){
                    PaymentOutReturnPurchaseDraftEntity newpiisd = new PaymentOutReturnPurchaseDraftEntity();
                    newpiisd.setReturnpurchase(invoice);
                    newpiisd.setPaymentoutdraft(paymentoutdraft);
                    newpiisd.setAmount(invoice.getRemaining() - piisd.getAmount());

                    retpaymentoutreturnpurchasedraftlist.add(newpiisd);
                }
            }
        }
        
        return ResponseEntity.ok(retpaymentoutreturnpurchasedraftlist);
    }
    
    @RequestMapping(value = "api/getPaymentOutReturnPurchaseDraft",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> 
        getPaymentOutReturnPurchaseDraft(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
            
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        ReturnPurchaseEntity returnpurchase = new ReturnPurchaseEntity();
        returnpurchase.setId(invoiceid);
            
        PaymentOutReturnPurchaseDraftEntity piisde = paymentoutreturnpurchasedraftService.
                getPaymentOutReturnPurchaseDraftEntity(paymentoutdraft,
                        returnpurchase);
        
        return ResponseEntity.ok(piisde);
    }
        
    @RequestMapping(value = "api/savePaymentOutReturnPurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> savePaymentOutReturnPurchaseDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestBody List<PaymentOutReturnPurchaseDraftEntity> paymentoutreturnpurchasedraftlist) {
        
        PaymentOutDraftEntity pode = new PaymentOutDraftEntity();
        pode.setId(paymentid);
            
        for(PaymentOutReturnPurchaseDraftEntity piisdetemp : 
                paymentoutreturnpurchasedraftService.getPaymentOutReturnPurchaseDraftEntityList(pode)){
            paymentoutreturnpurchasedraftService.deletePaymentOutReturnPurchaseDraft(piisdetemp.getId());
        }

        for(PaymentOutReturnPurchaseDraftEntity piisdetemp : paymentoutreturnpurchasedraftlist){
                piisdetemp.setId(IdGenerator.generateId());
                paymentoutreturnpurchasedraftService.addPaymentOutReturnPurchaseDraft(piisdetemp);
        }
        
        return ResponseEntity.ok("");
    }
}
