/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.purnama.pureff.entity.LoginEntity;
import net.purnama.pureff.entity.RoleEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
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
public class UserController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public RoleEntity login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginEntity login) throws IOException{
        
        UserEntity user = userService.getUser(login.getUsername(), login.getPassword());
        WarehouseEntity warehouse = warehouseService.getWarehouse(login.getWarehouseid());   
        
        if(user != null && warehouse != null){
            
            for(WarehouseEntity temp : user.getWarehouses()){
                
            }
            
            String token = JwtUtil.generateToken(user.getId(), warehouse.getId());
//            String token = JwtUtil.generateToken(user);
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
        else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log in");
        }
        return user.getRole();
    }
    
    @RequestMapping(value = "/api/getUserList/{itemperpage}/{page}/{keyword}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UserEntity> getUserList(@PathVariable int itemperpage,
            @PathVariable int page, @PathVariable String keyword) {
        
        List<UserEntity> ls = userService.getUserList(itemperpage, page, keyword);
        return ls;
    }
    
    @RequestMapping(value = "/api/getUserList/{itemperpage}/{page}", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UserEntity> getUserList(@PathVariable int itemperpage,
            @PathVariable int page) {
        List<UserEntity> ls = userService.getUserList(itemperpage, page, "");
        return ls;
    }
    
    @RequestMapping(value = "/api/getUserList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<UserEntity> getUserList() {
        
        List<UserEntity> ls = userService.getUserList();
        return ls;
    }
    
    @RequestMapping(value = "api/getUser/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public UserEntity getUser(@PathVariable String id) {
        return userService.getUser(id);
    }
    
    @RequestMapping(value = "api/addUser", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public UserEntity addUser(HttpServletRequest httpRequest, @RequestBody UserEntity user) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        user.setId(IdGenerator.generateId());
        user.setCode(user.getId());
        user.setLastmodified(Calendar.getInstance());
        user.setLastmodifiedby(temp);
        
        userService.addUser(user);
        
        return user;
    }

    @RequestMapping(value = "api/updateUser", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateUser(HttpServletRequest httpRequest, @RequestBody UserEntity user) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        user.setLastmodifiedby(temp);
        
        userService.updateUser(user);
    }
    
    @RequestMapping(value = {"api/countUserList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countUserList(@PathVariable String keyword){
        return userService.countUserList(keyword);
    }
    
    @RequestMapping(value = {"api/countUserList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public int countUserList(){
        return userService.countUserList("");
    }
}