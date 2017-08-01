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
public class NumberingController {
    
    @Autowired
    NumberingService numberingService;
    
    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "api/getNumberingList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getNumberingList() {
        
        List<NumberingEntity> ls = numberingService.getNumberingList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveNumberingList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveNumberingList(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<NumberingEntity> ls = numberingService.getActiveNumberingList(wartemp);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getNumberingList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"menuid"})
    public ResponseEntity<?> getNumberingList(HttpServletRequest httpRequest,
            @RequestParam(value="menuid") int menuid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(menuid);
        
        List<NumberingEntity> ls = numberingService.getNumberingList(wartemp, menu);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveNumberingList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"menuid"})
    public ResponseEntity<?> getActiveNumberingList(HttpServletRequest httpRequest,
            @RequestParam(value="menuid") int menuid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = new MenuEntity();
        menu.setId(menuid);
        
        List<NumberingEntity> ls = numberingService.getActiveNumberingList(wartemp, menu);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/setDefaultNumbering", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> setDefaultNumbering(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        NumberingEntity numbering = numberingService.getNumbering(id);
        
        if(numbering.getCurrent() == numbering.getEnd()){
            return ResponseEntity.badRequest().body("Numbering has reach its limit");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        numbering.setStatus(true);
        numbering.setLastmodifiedby(temp);
        
        MenuEntity menu = numbering.getMenu();
        
        NumberingEntity defnum = numberingService.getDefaultNumbering(menu, wartemp);
        if(defnum != null){
            defnum.setStatus(false);
            defnum.setLastmodifiedby(temp);
            numberingService.updateNumbering(defnum);
        }
        
        numberingService.updateNumbering(numbering);
        
        return ResponseEntity.ok(numbering);
    }
    
    @RequestMapping(value = "api/getNumbering", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getNumbering(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(numberingService.getNumbering(id));
    }

    @RequestMapping(value = "api/addNumbering", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addNumbering(HttpServletRequest httpRequest,
            @RequestBody NumberingEntity numbering) {
        
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity wartemp = new WarehouseEntity();
        wartemp.setId(JwtUtil.parseToken2(header.substring(7)));
        
        if(numberingService.getNumbering(numbering.getPrefix(), 
                numbering.getNumberingname(), wartemp, numbering.getMenu()) != null){
            return ResponseEntity.badRequest().body("Numbering '" + numbering.getPrefix() +"' already exist");
        }
        
        numbering.setId(IdGenerator.generateId());
        numbering.setLastmodified(Calendar.getInstance());
        numbering.setLastmodifiedby(temp);
        numbering.setWarehouse(wartemp);
        
        numberingService.addNumbering(numbering);
        
        return ResponseEntity.ok(numbering);
    }

    @RequestMapping(value = "api/updateNumbering", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateNumbering(HttpServletRequest httpRequest,
            @RequestBody NumberingEntity numbering) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numbering.setLastmodified(Calendar.getInstance());
        numbering.setLastmodifiedby(temp);
        
        numberingService.updateNumbering(numbering);
        
        return ResponseEntity.ok(numbering);
    }
}
