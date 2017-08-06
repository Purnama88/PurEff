/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.purnama.pureff.entity.LoginEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.GlobalFunctions;
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
public class UserController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, 
            @RequestBody LoginEntity login) throws Exception{
        
        String username = login.getUsername();
        String password = net.purnama.pureff.util.GlobalFunctions.encrypt(login.getPassword());
        
        UserEntity user = userService.getUser(username, password);
        WarehouseEntity warehouse = warehouseService.getWarehouse(login.getWarehouseid());   
        
        if(user != null && warehouse != null && user.isStatus()){
            
            boolean contain = false;
            
            for(WarehouseEntity temp : user.getWarehouses()){
                if(temp.getId().equals(warehouse.getId())){
                    contain = true;
                }
            }
            
            if(contain){
                String token = JwtUtil.generateToken(user.getId(), warehouse.getId());
                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

                return ResponseEntity.ok(user.getRole());
            }
            else{
                return ResponseEntity.badRequest().body("Invalid Log In");
            }
        }
        else{
            return ResponseEntity.badRequest().body("Invalid Log In");
        }
    }
    
    @RequestMapping(value = "/api/getUserList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getUserList() {
        
        List<UserEntity> ls = userService.getUserList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getUser", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getUser(@RequestParam(value="id") String id) {
        
        return ResponseEntity.ok(userService.getUser(id));
    }
    
    @RequestMapping(value = "api/addUser", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addUser(HttpServletRequest httpRequest,
            @RequestBody UserEntity user){
        
        user.setUsername(user.getUsername().toLowerCase());
        
        if(userService.getUserByUsername(user.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username '" + user.getUsername() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        user.setId(IdGenerator.generateId());
        user.setCode(user.getId());
        user.setLastmodified(Calendar.getInstance());
        user.setLastmodifiedby(temp);
        
        try{
            String password = user.getPassword();
            user.setPassword(net.purnama.pureff.util.GlobalFunctions.encrypt(password));
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("");
        }
        
        userService.addUser(user);
        
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "api/resetUserPassword", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> resetUserPassword(HttpServletRequest httpRequest, 
            @RequestParam(value="id") String id) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        UserEntity user = userService.getUser(id);
        
        String password = GlobalFunctions.getRandomString();
        
        try{
            user.setPassword(GlobalFunctions.encrypt(password));
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("");
        }
        
        user.setLastmodifiedby(temp);
        
        userService.updateUser(user);
        
        return ResponseEntity.ok(password);
    }
    
    @RequestMapping(value = "api/changeUserPassword", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> changeUserPassword(HttpServletRequest httpRequest, 
            @RequestBody UserEntity user) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        user.setLastmodifiedby(temp);
        
        userService.updateUser(user);
        
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping(value = "api/updateUser", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateUser(HttpServletRequest httpRequest, @RequestBody UserEntity user) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        user.setLastmodifiedby(temp);
        
        userService.updateUser(user);
        
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping(value = "/api/getUserList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getUserList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<UserEntity> ls = userService.getUserList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countUserList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countUserList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(userService.countUserList(keyword));
    }
}