/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.pureff.service.PaymentInReturnSalesDraftService;
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
public class PaymentInReturnSalesDraftController {
    
    @Autowired
    PaymentInReturnSalesDraftService paymentinreturnsalesdraftService;
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentInReturnSalesDraftEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentid);
        
        List<PaymentInReturnSalesDraftEntity> ls = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntityList(paymentindraft);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraftEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInReturnSalesDraftEntity(
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
    
}
