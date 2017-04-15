/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
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
public class NumberingController {
    
    @Autowired
    NumberingService numberingService;
    
    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "api/getNumberingList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingEntity> getNumberingList() {
        
        List<NumberingEntity> ls = numberingService.getNumberingList();
        return ls;
    }
    
    @RequestMapping(value = "api/getNumberingList/{menuid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingEntity> getNumberingList(HttpServletRequest httpRequest,
            @PathVariable int menuid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(menuid);
        
        List<NumberingEntity> ls = numberingService.getNumberingList(wartemp, menu);
        return ls;
    }
    
    @RequestMapping(value = "api/getNumbering/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public NumberingEntity getNumbering(@PathVariable String id) {
        return numberingService.getNumbering(id);
    }

    @RequestMapping(value = "api/addNumbering", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public NumberingEntity addNumbering(HttpServletRequest httpRequest,
            @RequestBody NumberingEntity numbering) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        numbering.setId(IdGenerator.generateId());
        numbering.setLastmodified(Calendar.getInstance());
        numbering.setLastmodifiedby(temp);
        numbering.setWarehouse(wartemp);
        
        numberingService.addNumbering(numbering);
        
        return numbering;
    }

    @RequestMapping(value = "api/updateNumbering", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public NumberingEntity updateNumbering(HttpServletRequest httpRequest,
            @RequestBody NumberingEntity numbering) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numbering.setLastmodified(Calendar.getInstance());
        numbering.setLastmodifiedby(temp);
        
        numberingService.updateNumbering(numbering);
        
        return numbering;
    }
}
