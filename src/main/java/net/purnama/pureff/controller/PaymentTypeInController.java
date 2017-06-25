/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentTypeInService;
import net.purnama.pureff.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
public class PaymentTypeInController {
    
    @Autowired
    PaymentTypeInService paymenttypeinService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @RequestMapping(value = "api/getPaymentTypeInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentTypeInList(@RequestParam(value="paymentid") String paymentid){
        
        PaymentInEntity paymentin = paymentinService.getPaymentIn(paymentid);
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPaymentTypeInList(paymentin);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPendingPaymentTypeInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"type"})
    public ResponseEntity<?> getPendingPaymentTypeInList(@RequestParam(value="type") int type) {
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPendingPaymentTypeInList(type);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentTypeInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"startdate", "enddate", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeInList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPaymentTypeInList(type, accepted,
                valid, CalendarUtil.toStartOfDay(start), CalendarUtil.toEndofDay(end));
        return ResponseEntity.ok(ls);
    }
}
