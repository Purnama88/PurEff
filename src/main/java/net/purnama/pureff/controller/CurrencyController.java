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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class CurrencyController {
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getCurrencyList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<CurrencyEntity> getCurrencyList() {
        
        List<CurrencyEntity> ls = currencyService.getCurrencyList();
        return ls;
    }
    
    @RequestMapping(value = "api/getCurrency/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public CurrencyEntity getCurrency(@PathVariable String id) {
        return currencyService.getCurrency(id);
    }
    
    @RequestMapping(value = "api/getCurrencyByCode/{code}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public CurrencyEntity getCurrencyByCode(@PathVariable String code) {
        return currencyService.getCurrency(code);
    }

    @RequestMapping(value = "api/addCurrency", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public CurrencyEntity addCurrency(HttpServletRequest httpRequest, @RequestBody CurrencyEntity currency) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        currency.setId(IdGenerator.generateId());
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(temp);
        
        currencyService.addCurrency(currency);
        
        return currency;
    }

    @RequestMapping(value = "api/updateCurrency", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public CurrencyEntity updateCurrency(HttpServletRequest httpRequest, 
            @RequestBody CurrencyEntity currency) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(temp);
        
        currencyService.updateCurrency(currency);
        
        return currency;
    }
    
    @RequestMapping(value = "/api/getCurrencyList/{itemperpage}/{page}/{keyword}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<CurrencyEntity> getCurrencyList(@PathVariable int itemperpage,
            @PathVariable int page, @PathVariable String keyword) {
        
        List<CurrencyEntity> ls = currencyService.getCurrencyList(itemperpage, page, keyword);
        return ls;
    }
    
    @RequestMapping(value = "/api/getCurrencyList/{itemperpage}/{page}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<CurrencyEntity> getCurrencyList(@PathVariable int itemperpage,
            @PathVariable int page) {
        List<CurrencyEntity> ls = currencyService.getCurrencyList(itemperpage, page, "");
        return ls;
    }
    
    @RequestMapping(value = {"api/countCurrencyList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countCurrencyList(@PathVariable String keyword){
        return currencyService.countCurrencyList(keyword);
    }
    
    @RequestMapping(value = {"api/countCurrencyList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countCurrencyList(){
        return currencyService.countCurrencyList("");
    }
}
