/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.UomService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UomController {
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getActiveParentUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UomEntity> getActiveParentUomList() {
        List<UomEntity> ls = uomService.getActiveParentUomList();
        System.out.println(ls.size());
        return ls;
    }
    
    @RequestMapping(value = "api/getParentUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UomEntity> getParentUomList() {
        
        List<UomEntity> ls = uomService.getParentUomList();
        return ls;
    }
    
    @RequestMapping(value = "api/getUomList/{parentid}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UomEntity> getUomList(@PathVariable String parentid) {
        UomEntity uom = uomService.getUom(parentid);
        List<UomEntity> ls = uomService.getUomList(uom);
        return ls;
    }
    
    @RequestMapping(value = "api/getUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UomEntity> getUomList() {
        List<UomEntity> ls = uomService.getUomList();
        return ls;
    }
    
    @RequestMapping(value = "api/getActiveUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UomEntity> getActiveUomList() {
        List<UomEntity> ls = uomService.getActiveUomList();
        return ls;
    }
    
    @RequestMapping(value = "api/getUom/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public UomEntity getUom(@PathVariable String id) {
        return uomService.getUom(id);
    }

    @RequestMapping(value = "api/addUom", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public UomEntity addUom(HttpServletRequest httpRequest, @RequestBody UomEntity uom) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        uom.setId(IdGenerator.generateId());
        uom.setLastmodified(Calendar.getInstance());
        uom.setLastmodifiedby(user);
        
        uomService.addUom(uom);
        
        return uom;
    }

    @RequestMapping(value = "api/updateUom", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public UomEntity updateUom(HttpServletRequest httpRequest,
            @RequestBody UomEntity uom) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        uom.setLastmodified(Calendar.getInstance());
        uom.setLastmodifiedby(user);
        
        uomService.updateUom(uom);
        
        return uom;
    }
}
