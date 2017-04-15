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
import net.purnama.pureff.entity.RateEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.RateService;
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
public class RateController {
    
    @Autowired
    RateService rateService;
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getLastRate/{currencyid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public RateEntity getLastRate(@PathVariable String currencyid) {
        
        CurrencyEntity currency = currencyService.getCurrency(currencyid);
        
        return rateService.getLastRate(currency);
        
    }
    
    @RequestMapping(value = "api/getRateList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<RateEntity> getRateList() {
        
        List<RateEntity> ls = rateService.getRateList();
        return ls;
    }
    
    @RequestMapping(value = "api/getRate/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public RateEntity getRate(@PathVariable String id) {
        return rateService.getRate(id);
    }

    @RequestMapping(value = "api/addRate", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public RateEntity addRate(HttpServletRequest httpRequest, @RequestBody RateEntity rate) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        rate.setId(IdGenerator.generateId());
        rate.setDate(Calendar.getInstance());
        rate.setLastmodified(Calendar.getInstance());
        rate.setLastmodifiedby(temp);
        
        rateService.addRate(rate);
        
        return rate;
    }

    @RequestMapping(value = "api/updateRate", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateRate(@RequestBody RateEntity rate) {
        rateService.updateRate(rate);
    }
}
