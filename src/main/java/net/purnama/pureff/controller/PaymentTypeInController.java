/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentTypeInService;
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
    
//    @RequestMapping(value = "api/getPendingPaymentTypeInList", method = RequestMethod.GET, 
//            headers = "Accept=application/json", params = {"type"})
//    public ResponseEntity<?> getPendingPaymentTypeInList(@RequestParam(value="type") int type) {
//        
//        List<PaymentTypeInEntity> ls = paymenttypeinService.getPendingPaymentTypeInList(type);
//        return ResponseEntity.ok(ls);
//    }
    
    @RequestMapping(value = "api/getPaymentTypeInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"startdate", "enddate", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeInList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.getPaymentTypeInList(type, accepted,
                valid, CalendarUtil.toStartOfDay(start), CalendarUtil.toEndOfDay(end));
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentTypeInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {
                "startdate", "enddate", "warehouseid", "partnerid",
                "currencyid", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeInList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.
                getPaymentTypeInList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, type,
                        valid, accepted);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/savePaymentTypeInList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentTypeInList(
            HttpServletRequest httpRequest,
            @RequestBody List<PaymentTypeInEntity> paymenttypeinlist) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        for(PaymentTypeInEntity paymenttypein : paymenttypeinlist){
            paymenttypein.setLastmodifiedby(temp);
            if(paymenttypein.getId() != null){
                paymenttypeinService.updatePaymentTypeIn(paymenttypein);
            }
            else{
                paymenttypein.setId(IdGenerator.generateId());
                paymenttypeinService.addPaymentTypeIn(paymenttypein);
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getPaymentTypeInList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", 
//                "type"
//                "valid", "accepted"
            })
    public ResponseEntity<?> getPaymentTypeInList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid
//            ,@RequestParam(value="type") int type
//            ,@RequestParam(value="valid") boolean valid,
//            @RequestParam(value="accepted") boolean accepted
    ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentTypeInEntity> ls = paymenttypeinService.
                getPaymentTypeInList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency
//                        ,type
//                        , valid, accepted
                );
        
        return ResponseEntity.ok(ls);
    }
}
