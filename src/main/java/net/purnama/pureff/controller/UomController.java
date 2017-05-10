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
public class UomController {
    
    @Autowired
    UomService uomService;
    
    @RequestMapping(value = "api/getActiveParentUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveParentUomList() {
        List<UomEntity> ls = uomService.getActiveParentUomList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getParentUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getParentUomList() {
        
        List<UomEntity> ls = uomService.getParentUomList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"parentid"})
    public ResponseEntity<?> getUomList(@RequestParam(value="parentid") String parentid) {
        UomEntity uom = uomService.getUom(parentid);
        List<UomEntity> ls = uomService.getUomList(uom);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getUomList() {
        List<UomEntity> ls = uomService.getUomList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveUomList() {
        List<UomEntity> ls = uomService.getActiveUomList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getUom", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getUom(@RequestParam(value="id") String id) {
        
        return ResponseEntity.ok(uomService.getUom(id));
    }

    @RequestMapping(value = "api/addUom", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addUom(HttpServletRequest httpRequest, @RequestBody UomEntity uom) {
        
        if(uomService.getUomByName(uom.getName()) != null){
            return ResponseEntity.badRequest().body("Name '" + uom.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        uom.setId(IdGenerator.generateId());
        uom.setLastmodified(Calendar.getInstance());
        uom.setLastmodifiedby(user);
        
        uomService.addUom(uom);
        
        return ResponseEntity.ok(uom);
    }

    @RequestMapping(value = "api/updateUom", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateUom(HttpServletRequest httpRequest,
            @RequestBody UomEntity uom) {
        
        UomEntity prev = uomService.getUomByName(uom.getName());
        
        if(prev != null && !prev.getId().equals(uom.getId())){
            return ResponseEntity.badRequest().body("Name '" + uom.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        uom.setLastmodified(Calendar.getInstance());
        uom.setLastmodifiedby(user);
        
        uomService.updateUom(uom);
        
        return ResponseEntity.ok(uom);
    }
    
    @RequestMapping(value = "/api/getParentUomList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getParentUomList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<UomEntity> ls = uomService.getUomList(itemperpage, page, sort, keyword, null);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countParentUomList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countParentUomList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(uomService.countUomList(keyword, null));
    }
}
