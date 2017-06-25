/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import net.purnama.pureff.entity.PartnerTypeEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PartnerTypeService;
import net.purnama.pureff.util.GlobalFields;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PartnerTypeController {
    @Autowired
    PartnerTypeService partnertypeService;
    
    @RequestMapping(value = "api/getPartnerTypeParentList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPartnerTypeParentList() {
        
        List<String> ls = new ArrayList<>();
        
        for(String name : GlobalFields.PARENT_NAMES){
            ls.add(name);
        }
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActivePartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"parent"})
    public ResponseEntity<?> getActivePartnerTypeList(@RequestParam(value="parent") int parent) {
        
        List<PartnerTypeEntity> ls = partnertypeService.getActivePartnerTypeList(parent);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"parent"})
    public ResponseEntity<?> getPartnerTypeList(@RequestParam(value="parent") int parent) {
        
        List<PartnerTypeEntity> ls = partnertypeService.getPartnerTypeList(parent);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActivePartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActivePartnerTypeList() {
        
        List<PartnerTypeEntity> ls = partnertypeService.getActivePartnerTypeList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPartnerTypeList() {
        
        List<PartnerTypeEntity> ls = partnertypeService.getPartnerTypeList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPartnerType", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPartnerType(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(partnertypeService.getPartnerType(id));
    }

    @RequestMapping(value = "api/addPartnerType", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addPartnerType(HttpServletRequest httpRequest, @RequestBody PartnerTypeEntity partnertype) {
        
        if(partnertypeService.getPartnerTypeByName(partnertype.getName()) != null){
            return ResponseEntity.badRequest().body("Name '" + partnertype.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partnertype.setId(IdGenerator.generateId());
        partnertype.setLastmodified(Calendar.getInstance());
        partnertype.setLastmodifiedby(user);
        
        partnertypeService.addPartnerType(partnertype);
        
        return ResponseEntity.ok(partnertype);
    }

    @RequestMapping(value = "api/updatePartnerType", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePartnerType(HttpServletRequest httpRequest, 
            @RequestBody PartnerTypeEntity partnertype) {
        
        PartnerTypeEntity prev = partnertypeService.getPartnerTypeByName(partnertype.getName());
        
        if(prev != null && !prev.getId().equals(partnertype.getId())){
            return ResponseEntity.badRequest().body("Name '" + partnertype.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partnertype.setLastmodified(Calendar.getInstance());
        partnertype.setLastmodifiedby(user);
        
        partnertypeService.updatePartnerType(partnertype);
        
        return ResponseEntity.ok(partnertype);
    }
    
    @RequestMapping(value = "/api/getPartnerTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPartnerTypeList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PartnerTypeEntity> ls = partnertypeService.getPartnerTypeList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPartnerTypeList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPartnerTypeList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(partnertypeService.countPartnerTypeList(keyword));
    }
}
