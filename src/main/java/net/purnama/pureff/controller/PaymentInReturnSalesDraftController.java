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
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.pureff.service.PaymentInReturnSalesDraftService;
import net.purnama.pureff.service.ReturnSalesService;
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
public class PaymentInReturnSalesDraftController {
    
    @Autowired
    PaymentInReturnSalesDraftService paymentinreturnsalesdraftService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @RequestMapping(value = "api/getSelectedPaymentInReturnSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getSelectedPaymentInReturnSalesDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentInReturnSalesDraftEntity> ls = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntityList(paymentindraft, partner, currency);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid", "partnerid", "currencyid"})
    public ResponseEntity<?> getPaymentInReturnSalesDraftList(
            @RequestParam(value="paymentid")String paymentid,
            @RequestParam(value="partnerid")String partnerid,
            @RequestParam(value="currencyid")String currencyid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
                
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<ReturnSalesEntity> returnsaleslist = returnsalesService.
                getUnpaidReturnSalesList(partner, currency);
        
        List<PaymentInReturnSalesDraftEntity> retpaymentinreturnsalesdraftlist =
                new ArrayList<>();
        
        for(ReturnSalesEntity returnsales : returnsaleslist){
                
            PaymentInReturnSalesDraftEntity piisd = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntity(paymentindraft,
                        returnsales);

            if(piisd == null){
                PaymentInReturnSalesDraftEntity newpiisd = new PaymentInReturnSalesDraftEntity();
                newpiisd.setReturnsales(returnsales);
                newpiisd.setPaymentindraft(paymentindraft);
                newpiisd.setAmount(returnsales.getRemaining());
                retpaymentinreturnsalesdraftlist.add(newpiisd);
            }
            else{
                if(returnsales.getRemaining() - piisd.getAmount() > 0){
                    PaymentInReturnSalesDraftEntity newpiisd = new PaymentInReturnSalesDraftEntity();
                    newpiisd.setReturnsales(returnsales);
                    newpiisd.setPaymentindraft(paymentindraft);
                    newpiisd.setAmount(returnsales.getRemaining() - piisd.getAmount());

                    retpaymentinreturnsalesdraftlist.add(newpiisd);
                }
            }
        }
        
        return ResponseEntity.ok(retpaymentinreturnsalesdraftlist);
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraft",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInReturnSalesDraft(
                @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        ReturnSalesEntity returnsales = new ReturnSalesEntity();
        returnsales.setId(invoiceid);
        
        PaymentInReturnSalesDraftEntity piisde = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntity(paymentindraft,
                        returnsales);
        
        return ResponseEntity.ok(piisde);
    }
    
    @RequestMapping(value = "api/savePaymentInReturnSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> savePaymentInReturnSalesDraftList(
            @RequestParam(value="paymentid") String paymentid,
            @RequestBody List<PaymentInReturnSalesDraftEntity> paymentinreturnsalesdraftlist) {
        
        PaymentInDraftEntity pide = new PaymentInDraftEntity();
        pide.setId(paymentid);
        
        for(PaymentInReturnSalesDraftEntity piisdetemp : 
                paymentinreturnsalesdraftService.getPaymentInReturnSalesDraftEntityList(pide)){
            paymentinreturnsalesdraftService.deletePaymentInReturnSalesDraft(piisdetemp.getId());
        }

        for(PaymentInReturnSalesDraftEntity piisdetemp : paymentinreturnsalesdraftlist){
                piisdetemp.setId(IdGenerator.generateId());
                paymentinreturnsalesdraftService.addPaymentInReturnSalesDraft(piisdetemp);
        }
        
        return ResponseEntity.ok("");
    }
}
