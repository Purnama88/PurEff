/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.NumberingNameEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.NumberingNameService;
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
public class NumberingNameController {
    
    @Autowired
    NumberingNameService numberingnameService;
    
    @RequestMapping(value = "api/getNumberingNameList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingNameEntity> getNumberingNameList() {
        
        List<NumberingNameEntity> ls = numberingnameService.getNumberingNameList();
        return ls;
    }
    
    @RequestMapping(value = "api/getActiveNumberingNameList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingNameEntity> getActiveNumberingNameList() {
        
        List<NumberingNameEntity> ls = numberingnameService.getActiveNumberingNameList();
        return ls;
    }
    
    @RequestMapping(value = "api/getNumberingName/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public NumberingNameEntity getNumberingName(@PathVariable String id) {
        return numberingnameService.getNumberingName(id);
    }

    @RequestMapping(value = "api/addNumberingName", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public NumberingNameEntity addNumberingName(HttpServletRequest httpRequest,
            @RequestBody NumberingNameEntity numberingname) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numberingname.setId(IdGenerator.generateId());
        numberingname.setLastmodified(Calendar.getInstance());
        numberingname.setLastmodifiedby(temp);
        
        numberingnameService.addNumberingName(numberingname);
        
        return numberingname;
    }

    @RequestMapping(value = "api/updateNumberingName", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public NumberingNameEntity updateNumberingName(HttpServletRequest httpRequest,
            @RequestBody NumberingNameEntity numberingname) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numberingname.setLastmodified(Calendar.getInstance());
        numberingname.setLastmodifiedby(temp);
        
        numberingnameService.updateNumberingName(numberingname);
        
        return numberingname;
    }
    
    @RequestMapping(value = "/api/getNumberingNameList/{itemperpage}/{page}/{keyword}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingNameEntity> getNumberingNameList(@PathVariable int itemperpage,
            @PathVariable int page, @PathVariable String keyword) {
        
        List<NumberingNameEntity> ls = numberingnameService.getNumberingNameList(itemperpage, page, keyword);
        return ls;
    }
    
    @RequestMapping(value = "/api/getNumberingNameList/{itemperpage}/{page}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<NumberingNameEntity> getNumberingNameList(@PathVariable int itemperpage,
            @PathVariable int page) {
        List<NumberingNameEntity> ls = numberingnameService.getNumberingNameList(itemperpage, page, "");
        return ls;
    }
    
    @RequestMapping(value = {"api/countNumberingNameList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countNumberingNameList(@PathVariable String keyword){
        return numberingnameService.countNumberingNameList(keyword);
    }
    
    @RequestMapping(value = {"api/countNumberingNameList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countNumberingNameList(){
        return numberingnameService.countNumberingNameList("");
    }
}
