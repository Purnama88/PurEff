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
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PartnerService;
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
public class PartnerController {
    @Autowired
    PartnerService partnerService;
    
    @RequestMapping(value = "api/getActivePartnerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerEntity> getActivePartnerList() {
        
        List<PartnerEntity> ls = partnerService.getActivePartnerList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPartnerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerEntity> getPartnerList() {
        
        List<PartnerEntity> ls = partnerService.getPartnerList();
        return ls;
    }
    
    @RequestMapping(value = "api/getPartner/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public PartnerEntity getPartner(@PathVariable String id) {
        return partnerService.getPartner(id);
    }

    @RequestMapping(value = "api/addPartner", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public PartnerEntity addPartner(HttpServletRequest httpRequest, @RequestBody PartnerEntity partner) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partner.setId(IdGenerator.generateId());
        partner.setLastmodified(Calendar.getInstance());
        partner.setLastmodifiedby(user);
        
        partnerService.addPartner(partner);
        
        return partner;
    }

    @RequestMapping(value = "api/updatePartner", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public PartnerEntity updatePartner(HttpServletRequest httpRequest,
            @RequestBody PartnerEntity partner) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partner.setLastmodified(Calendar.getInstance());
        partner.setLastmodifiedby(user);
        
        partnerService.updatePartner(partner);
        
        return partner;
    }
    
    @RequestMapping(value = "/api/getPartnerList/{itemperpage}/{page}/{keyword}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerEntity> getPartnerList(@PathVariable int itemperpage,
            @PathVariable int page, @PathVariable String keyword) {
        
        List<PartnerEntity> ls = partnerService.getPartnerList(itemperpage, page, keyword);
        return ls;
    }
    
    @RequestMapping(value = "/api/getPartnerList/{itemperpage}/{page}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PartnerEntity> getPartnerList(@PathVariable int itemperpage,
            @PathVariable int page) {
        List<PartnerEntity> ls = partnerService.getPartnerList(itemperpage, page, "");
        return ls;
    }
    
    @RequestMapping(value = {"api/countPartnerList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countPartnerList(@PathVariable String keyword){
        return partnerService.countPartnerList(keyword);
    }
    
    @RequestMapping(value = {"api/countPartnerList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countPartnerList(){
        return partnerService.countPartnerList("");
    }
}
