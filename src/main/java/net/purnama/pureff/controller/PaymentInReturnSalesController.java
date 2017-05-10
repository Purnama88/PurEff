/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
import net.purnama.pureff.service.PaymentInReturnSalesService;
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
public class PaymentInReturnSalesController {
    
    @Autowired
    PaymentInReturnSalesService paymentinreturnsalesService;
    
    @RequestMapping(value = "api/getPaymentInReturnSalesEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentInReturnSalesEntityList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(paymentid);
        
        List<PaymentInReturnSalesEntity> ls = paymentinreturnsalesService.
                getPaymentInReturnSalesEntityList(paymentin);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesEntity",
            method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid, invoiceid"})
    public ResponseEntity<?> getPaymentInReturnSalesEntity(
                @RequestParam(value="paymentid") String paymentid,
        @RequestParam(value="invoiceid") String invoiceid){
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(paymentid);
        
        ReturnSalesEntity returnsales = new ReturnSalesEntity();
        returnsales.setId(invoiceid);
        
        PaymentInReturnSalesEntity piisde = paymentinreturnsalesService.
                getPaymentInReturnSalesEntity(paymentin,
                        returnsales);
        
        return ResponseEntity.ok(piisde);
    }
    
}
