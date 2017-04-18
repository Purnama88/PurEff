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
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemGroupService;
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
public class ItemGroupController {
    
    @Autowired
    ItemGroupService itemgroupService;
    
    @RequestMapping(value = "api/getItemGroupList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ItemGroupEntity> getItemGroupList() {
        
        List<ItemGroupEntity> ls = itemgroupService.getItemGroupList();
        return ls;
    }
    
    @RequestMapping(value = "api/getActiveItemGroupList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ItemGroupEntity> getActiveItemGroupList() {
        
        List<ItemGroupEntity> ls = itemgroupService.getActiveItemGroupList();
        return ls;
    }
    
    @RequestMapping(value = "api/getItemGroup/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ItemGroupEntity getItemGroup(@PathVariable String id) {
        
        return itemgroupService.getItemGroup(id);
    }

    @RequestMapping(value = "api/addItemGroup", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ItemGroupEntity addItemGroup(HttpServletRequest httpRequest,
            @RequestBody ItemGroupEntity itemgroup) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        itemgroup.setId(IdGenerator.generateId());
        itemgroup.setLastmodified(Calendar.getInstance());
        itemgroup.setLastmodifiedby(user);
        
        itemgroupService.addItemGroup(itemgroup);
        
        return itemgroup;
    }

    @RequestMapping(value = "api/updateItemGroup", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ItemGroupEntity updateItemGroup(HttpServletRequest httpRequest,
            @RequestBody ItemGroupEntity itemgroup) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        itemgroup.setLastmodified(Calendar.getInstance());
        itemgroup.setLastmodifiedby(user);
        
        itemgroupService.updateItemGroup(itemgroup);
        
        return itemgroup;
    }

    @RequestMapping(value = "api/deleteItemGroup/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteItemGroup(@PathVariable String id) {
        itemgroupService.deleteItemGroup(id);		
    }
    
    @RequestMapping(value = "/api/getItemGroupList/{itemperpage}/{page}/{keyword}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ItemGroupEntity> getItemGroupList(@PathVariable int itemperpage,
            @PathVariable int page, @PathVariable String keyword) {
        
        List<ItemGroupEntity> ls = itemgroupService.getItemGroupList(itemperpage, page, keyword);
        return ls;
    }
    
    @RequestMapping(value = "/api/getItemGroupList/{itemperpage}/{page}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ItemGroupEntity> getItemGroupList(@PathVariable int itemperpage,
            @PathVariable int page) {
        List<ItemGroupEntity> ls = itemgroupService.getItemGroupList(itemperpage, page, "");
        return ls;
    }
    
    @RequestMapping(value = {"api/countItemGroupList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countItemGroupList(@PathVariable String keyword){
        return itemgroupService.countItemGroupList(keyword);
    }
    
    @RequestMapping(value = {"api/countItemGroupList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countItemGroupList(){
        return itemgroupService.countItemGroupList("");
    }
}
