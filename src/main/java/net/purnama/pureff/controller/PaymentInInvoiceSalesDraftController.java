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
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.PaymentInInvoiceSalesDraftService;
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
public class PaymentInInvoiceSalesDraftController {
    
    @Autowired
    PaymentInInvoiceSalesDraftService paymentininvoicesalesdraftService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getSelectedPaymentInInvoiceSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getSelectedPaymentInInvoiceSalesDraftList(
            @RequestParam(value="paymentid")String paymentid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        List<PaymentInInvoiceSalesDraftEntity> ls = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesDraftList(
            @RequestParam(value="paymentid")String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<InvoiceSalesEntity> invoicesaleslist = invoicesalesService.
                getUnpaidInvoiceSalesList(partner, currency);
        
        List<PaymentInInvoiceSalesDraftEntity> retpaymentininvoicesalesdraftlist =
                new ArrayList<>();
        
        for(InvoiceSalesEntity invoice : invoicesaleslist){
            
            PaymentInInvoiceSalesDraftEntity piisd = paymentininvoicesalesdraftService.
                        getPaymentInInvoiceSalesDraftEntity(paymentindraft, invoice);

            if(piisd == null){
                PaymentInInvoiceSalesDraftEntity newpiisd = new PaymentInInvoiceSalesDraftEntity();
                newpiisd.setInvoicesales(invoice);
                newpiisd.setPaymentindraft(paymentindraft);
                newpiisd.setAmount(invoice.getRemaining());
                retpaymentininvoicesalesdraftlist.add(newpiisd);
            }
            else{
                if(invoice.getRemaining() - piisd.getAmount() > 0){
                    PaymentInInvoiceSalesDraftEntity newpiisd = new PaymentInInvoiceSalesDraftEntity();
                    newpiisd.setInvoicesales(invoice);
                    newpiisd.setPaymentindraft(paymentindraft);
                    newpiisd.setAmount(invoice.getRemaining() - piisd.getAmount());

                    retpaymentininvoicesalesdraftlist.add(newpiisd);
                }
            }
        }
        
        return ResponseEntity.ok(retpaymentininvoicesalesdraftlist);
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraft",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInInvoiceSalesDraft(
        @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        InvoiceSalesEntity invoicesales = new InvoiceSalesEntity();
        invoicesales.setId(invoiceid);
        
        PaymentInInvoiceSalesDraftEntity piisde = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntity(paymentindraft,
                        invoicesales);
        
        return ResponseEntity.ok(piisde);
    }
    
    @RequestMapping(value = "api/savePaymentInInvoiceSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentInInvoiceSalesDraftList(
            @RequestBody List<PaymentInInvoiceSalesDraftEntity> paymentininvoicesalesdraftlist) {
        
        if(!paymentininvoicesalesdraftlist.isEmpty()){
            PaymentInInvoiceSalesDraftEntity piisde = paymentininvoicesalesdraftlist.get(0);
            
            PaymentInDraftEntity pid = piisde.getPaymentindraft();
            
            for(PaymentInInvoiceSalesDraftEntity piisdetemp : 
                    paymentininvoicesalesdraftService.getPaymentInInvoiceSalesDraftEntityList(pid)){
                paymentininvoicesalesdraftService.deletePaymentinInvoiceSalesDraft(piisdetemp.getId());
            }
            
            for(PaymentInInvoiceSalesDraftEntity piisdetemp : paymentininvoicesalesdraftlist){
                    piisdetemp.setId(IdGenerator.generateId());
                    paymentininvoicesalesdraftService.addPaymentinInvoiceSalesDraft(piisdetemp);
            }
        }
        return ResponseEntity.ok("");
    }
}
