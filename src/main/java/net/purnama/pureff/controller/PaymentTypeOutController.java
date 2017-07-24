/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.PaymentTypeOutService;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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
public class PaymentTypeOutController {
    
    @Autowired
    PaymentTypeOutService paymenttypeoutService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentTypeOutList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = paymentoutService.getPaymentOut(paymentid);
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPaymentTypeOutList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPendingPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"type"})
    public ResponseEntity<?> getPendingPaymentTypeOutList(@RequestParam(value="type") int type) {
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPendingPaymentTypeOutList(type);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"startdate", "enddate", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeOutList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPaymentTypeOutList(type, accepted,
                valid, CalendarUtil.toStartOfDay(start), CalendarUtil.toEndofDay(end));
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/updatePaymentTypeOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentTypeOut(HttpServletRequest httpRequest,
            @RequestBody PaymentTypeOutEntity paymenttypeout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        paymenttypeout.setLastmodified(Calendar.getInstance());
        paymenttypeout.setLastmodifiedby(user);
        
        paymenttypeoutService.updatePaymentTypeOut(paymenttypeout);
        
        return ResponseEntity.ok(paymenttypeout);
    }
    
    @RequestMapping(value = "api/savePaymentTypeOutList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentTypeOutList(
            HttpServletRequest httpRequest,
            @RequestBody List<PaymentTypeOutEntity> paymenttypeoutlist) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        for(PaymentTypeOutEntity paymenttypeout : paymenttypeoutlist){
            paymenttypeout.setLastmodifiedby(temp);
            if(paymenttypeout.getId() != null){
                paymenttypeoutService.updatePaymentTypeOut(paymenttypeout);
            }
            else{
                paymenttypeout.setId(IdGenerator.generateId());
                paymenttypeoutService.addPaymentTypeOut(paymenttypeout);
            }
        }
        
        return ResponseEntity.ok("");
    }
}
