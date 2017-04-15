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
import net.purnama.pureff.entity.PartnerTypeEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PartnerTypeService;
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
public class PartnerTypeController {
    @Autowired
    PartnerTypeService partnertypeService;
    
    @RequestMapping(value = "api/getActivePartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerTypeEntity> getActivePartnerTypeList() {
        
        List<PartnerTypeEntity> ls = partnertypeService.getActivePartnerTypeList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerTypeEntity> getPartnerTypeList() {
        
        List<PartnerTypeEntity> ls = partnertypeService.getPartnerTypeList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPartnerType/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PartnerTypeEntity getPartnerType(@PathVariable String id) {
        return partnertypeService.getPartnerType(id);
    }

    @RequestMapping(value = "api/addPartnerType", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PartnerTypeEntity addPartnerType(HttpServletRequest httpRequest, @RequestBody PartnerTypeEntity partnertype) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partnertype.setId(IdGenerator.generateId());
        partnertype.setLastmodified(Calendar.getInstance());
        partnertype.setLastmodifiedby(user);
        
        partnertypeService.addPartnerType(partnertype);
        
        return partnertype;
    }

    @RequestMapping(value = "api/updatePartnerType", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public PartnerTypeEntity updatePartnerType(HttpServletRequest httpRequest, 
            @RequestBody PartnerTypeEntity partnertype) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partnertype.setLastmodified(Calendar.getInstance());
        partnertype.setLastmodifiedby(user);
        
        partnertypeService.updatePartnerType(partnertype);
        
        return partnertype;
    }
}
