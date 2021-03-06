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
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseDraftService;
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
public class PaymentOutInvoicePurchaseDraftController {
    
    @Autowired
    PaymentOutInvoicePurchaseDraftService paymentoutinvoicepurchasedraftService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @RequestMapping(value = "api/getSelectedPaymentOutInvoicePurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getSelectedPaymentOutInvoicePurchaseDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentOutInvoicePurchaseDraftEntity> ls = paymentoutinvoicepurchasedraftService.
                getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft, partner, currency);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getPaymentOutInvoicePurchaseDraftList(
            @RequestParam(value="paymentid")String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<InvoicePurchaseEntity> invoicepurchaselist = invoicepurchaseService.
                getUnpaidInvoicePurchaseList(partner, currency);
        
        List<PaymentOutInvoicePurchaseDraftEntity> retpaymentoutinvoicepurchasedraftlist =
                new ArrayList<>();
        
        for(InvoicePurchaseEntity invoice : invoicepurchaselist){
            
            PaymentOutInvoicePurchaseDraftEntity piisd = paymentoutinvoicepurchasedraftService.
                        getPaymentOutInvoicePurchaseDraftEntity(paymentoutdraft, invoice);

            if(piisd == null){
                PaymentOutInvoicePurchaseDraftEntity newpiisd = new PaymentOutInvoicePurchaseDraftEntity();
                newpiisd.setInvoicepurchase(invoice);
                newpiisd.setPaymentoutdraft(paymentoutdraft);
                newpiisd.setAmount(invoice.getRemaining());
                retpaymentoutinvoicepurchasedraftlist.add(newpiisd);
            }
            else{
                if(invoice.getRemaining() - piisd.getAmount() > 0){
                    PaymentOutInvoicePurchaseDraftEntity newpiisd = new PaymentOutInvoicePurchaseDraftEntity();
                    newpiisd.setInvoicepurchase(invoice);
                    newpiisd.setPaymentoutdraft(paymentoutdraft);
                    newpiisd.setAmount(invoice.getRemaining() - piisd.getAmount());

                    retpaymentoutinvoicepurchasedraftlist.add(newpiisd);
                }
            }
        }
        
        return ResponseEntity.ok(retpaymentoutinvoicepurchasedraftlist);
    }
    
    @RequestMapping(value = "api/getPaymentOutInvoicePurchaseDraft",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentOutInvoicePurchaseDraft(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentid);
        
        InvoicePurchaseEntity invoicepurchase = new InvoicePurchaseEntity();
        invoicepurchase.setId(invoiceid);
        
        PaymentOutInvoicePurchaseDraftEntity piisde = paymentoutinvoicepurchasedraftService.
                getPaymentOutInvoicePurchaseDraftEntity(paymentoutdraft,
                        invoicepurchase);
        
        return ResponseEntity.ok(piisde);
    }
    
    @RequestMapping(value = "api/savePaymentOutInvoicePurchaseDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> savePaymentOutInvoicePurchaseDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestBody List<PaymentOutInvoicePurchaseDraftEntity> paymentoutinvoicepurchasedraftlist) {
        
        PaymentOutDraftEntity pode = new PaymentOutDraftEntity();
        pode.setId(paymentid);
        
        for(PaymentOutInvoicePurchaseDraftEntity piisdetemp : 
                paymentoutinvoicepurchasedraftService.getPaymentOutInvoicePurchaseDraftEntityList(pode)){
            paymentoutinvoicepurchasedraftService.deletePaymentOutInvoicePurchaseDraft(piisdetemp.getId());
        }

        for(PaymentOutInvoicePurchaseDraftEntity piisdetemp : paymentoutinvoicepurchasedraftlist){
                piisdetemp.setId(IdGenerator.generateId());
                paymentoutinvoicepurchasedraftService.addPaymentOutInvoicePurchaseDraft(piisdetemp);
        }
        
        return ResponseEntity.ok("");
    }
}
