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
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemGroupService;
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
public class ItemGroupController {
    
    @Autowired
    ItemGroupService itemgroupService;
    
    @RequestMapping(value = "api/getItemGroupList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getItemGroupList() {
        
        List<ItemGroupEntity> ls = itemgroupService.getItemGroupList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveItemGroupList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveItemGroupList() {
        
        List<ItemGroupEntity> ls = itemgroupService.getActiveItemGroupList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getItemGroup", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemGroup(@RequestParam(value="id") String id) {
        
        return ResponseEntity.ok(itemgroupService.getItemGroup(id));
    }

    @RequestMapping(value = "api/addItemGroupList",method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addItemGroupList(HttpServletRequest httpRequest,
            @RequestBody List<ItemGroupEntity>itemgrouplist){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<ItemGroupEntity> returnlist = new ArrayList<>();
        
        for(ItemGroupEntity itemgroup : itemgrouplist){
            itemgroup.setId(IdGenerator.generateId());
            itemgroup.setLastmodified(Calendar.getInstance());
            itemgroup.setLastmodifiedby(user);

            try{
                itemgroupService.addItemGroup(itemgroup);
            }
            catch(Exception e){
                returnlist.add(itemgroup);
            }
        }
        
        return ResponseEntity.ok(returnlist);
    }
    
    @RequestMapping(value = "api/addItemGroup", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addItemGroup(HttpServletRequest httpRequest,
            @RequestBody ItemGroupEntity itemgroup) {
        
        if(itemgroupService.getItemGroupByCode(itemgroup.getCode()) != null){
            return ResponseEntity.badRequest().body("Code '" + itemgroup.getCode() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        itemgroup.setId(IdGenerator.generateId());
        itemgroup.setLastmodified(Calendar.getInstance());
        itemgroup.setLastmodifiedby(user);
        
        itemgroupService.addItemGroup(itemgroup);
        
        return ResponseEntity.ok(itemgroup);
    }

    @RequestMapping(value = "api/updateItemGroup", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateItemGroup(HttpServletRequest httpRequest,
            @RequestBody ItemGroupEntity itemgroup) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        itemgroup.setLastmodified(Calendar.getInstance());
        itemgroup.setLastmodifiedby(user);
        
        itemgroupService.updateItemGroup(itemgroup);
        
        return ResponseEntity.ok(itemgroup);
    }
    
    @RequestMapping(value = "/api/getItemGroupList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getItemGroupList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<ItemGroupEntity> ls = itemgroupService.getItemGroupList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countItemGroupList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countItemGroupList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(itemgroupService.countItemGroupList(keyword));
    }
}
