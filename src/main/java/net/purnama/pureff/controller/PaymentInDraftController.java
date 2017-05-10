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
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.RateEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.RateService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class PaymentInDraftController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    RateService rateService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    NumberingService numberingService;
    
    @RequestMapping(value = "api/getPaymentInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentInDraftList() {
        
        List<PaymentInDraftEntity> ls = paymentindraftService.getPaymentInDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentInDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentindraftService.getPaymentInDraft(id));
    }

    @RequestMapping(value = "api/addPaymentInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addPaymentInDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(15);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu incoming payment");
        }
        
        CurrencyEntity currency = currencyService.getDefaultCurrency();
        RateEntity rate = rateService.getLastRate(currency);
        Calendar date = Calendar.getInstance();
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(IdGenerator.generateId());
        paymentindraft.setDate(date);
        paymentindraft.setDuedate(date);
        paymentindraft.setAmount(0);
        paymentindraft.setWarehouse(warehouse);
        paymentindraft.setNote("");
        paymentindraft.setLastmodified(date);
        paymentindraft.setLastmodifiedby(user);
        paymentindraft.setCurrency(currencyService.getDefaultCurrency());
        if(rate == null){
            paymentindraft.setRate(1);
        }
        else{
            paymentindraft.setRate(rate.getValue());
        }
        
        paymentindraftService.addPaymentInDraft(paymentindraft);
        
        return ResponseEntity.ok(paymentindraft.getId());
    }

    @RequestMapping(value = "api/updatePaymentInDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentInDraft(HttpServletRequest httpRequest,
            @RequestBody PaymentInDraftEntity paymentindraft) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentindraft.setLastmodified(Calendar.getInstance());
        paymentindraft.setWarehouse(warehouse);
        paymentindraft.setLastmodifiedby(user);
        
        paymentindraftService.updatePaymentInDraft(paymentindraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deletePaymentInDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deletePaymentInDraft(@PathVariable String id) {
        paymentindraftService.deletePaymentInDraft(id);		
    }
    
    @RequestMapping(value = "/api/getPaymentInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<PaymentInDraftEntity> ls = paymentindraftService.
                getPaymentInDraftList(itemperpage, page, sort, keyword, user);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentInDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPaymentInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return ResponseEntity.ok(paymentindraftService.countPaymentInDraftList(keyword, user));
    }
    
}
