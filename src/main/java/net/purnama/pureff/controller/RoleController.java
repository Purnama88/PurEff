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
import net.purnama.pureff.entity.RoleEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.RoleService;
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
public class RoleController {
    
    @Autowired
    RoleService roleService;
    
    @RequestMapping(value = "/api/getRoleList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getRoleList() {
        
        List<RoleEntity> ls = roleService.getRoleList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "/api/getActiveRoleList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveRoleList() {
        
        List<RoleEntity> ls = roleService.getActiveRoleList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getRole", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getRole(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @RequestMapping(value = "api/addRole", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addRole(HttpServletRequest httpRequest, @RequestBody RoleEntity role) {
        
        if(roleService.getRoleByName(role.getName()) != null){
            return ResponseEntity.badRequest().body("Name '" + role.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        role.setId(IdGenerator.generateId());
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        
        roleService.addRole(role);
        
        return ResponseEntity.ok(role);
    }

    @RequestMapping(value = "api/updateRole", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateRole(HttpServletRequest httpRequest, @RequestBody RoleEntity role) {
        
        RoleEntity prev = roleService.getRoleByName(role.getName());
        
        if(prev != null && !prev.getId().equals(role.getId())){
            return ResponseEntity.badRequest().body("Name '" + role.getName() +"' already exist");
        }
        
        
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        
        roleService.updateRole(role);
        
        return ResponseEntity.ok(role);
    }
    
    @RequestMapping(value = "/api/getRoleList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getRoleList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<RoleEntity> ls = roleService.getRoleList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countRoleList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countRoleList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(roleService.countRoleList(keyword));
    }
}