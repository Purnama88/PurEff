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
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CurrencyController {
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getActiveCurrencyList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveCurrencyList() {
        
        List<CurrencyEntity> ls = currencyService.getActiveCurrencyList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getCurrencyList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getCurrencyList() {
        
        List<CurrencyEntity> ls = currencyService.getCurrencyList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getCurrency", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getCurrency(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(currencyService.getCurrency(id));
    }
    
    @RequestMapping(value = "api/addCurrency", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addCurrency(HttpServletRequest httpRequest, 
            @RequestBody CurrencyEntity currency) {
        
        if(currencyService.getCurrencyByCode(currency.getCode()) != null){
            return ResponseEntity.badRequest().body("Code '" + currency.getCode() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        currency.setId(IdGenerator.generateId());
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(temp);
        
        currencyService.addCurrency(currency);
        
        return ResponseEntity.ok(currency);
    }

    @RequestMapping(value = "api/updateCurrency", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateCurrency(HttpServletRequest httpRequest, 
            @RequestBody CurrencyEntity currency) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(temp);
        
        currencyService.updateCurrency(currency);
        
        return ResponseEntity.ok(currency);
    }
    
    @RequestMapping(value = "api/getCurrencyList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getCurrencyList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<CurrencyEntity> ls = currencyService.getCurrencyList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countCurrencyList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countCurrencyList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(currencyService.countCurrencyList(keyword));
    }
}
