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
public class RoleController {
    
    @Autowired
    RoleService roleService;
    
    @RequestMapping(value = "/api/getRoleList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<RoleEntity> getRoleList() {
        
        List<RoleEntity> ls = roleService.getRoleList();
        return ls;
    }
    
    @RequestMapping(value = "/api/getActiveRoleList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<RoleEntity> getActiveRoleList() {
        
        List<RoleEntity> ls = roleService.getRoleList();
        return ls;
    }
    
    @RequestMapping(value = "api/getRole/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public RoleEntity getRole(@PathVariable String id) {
        return roleService.getRole(id);
    }

    @RequestMapping(value = "api/addRole", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public RoleEntity addRole(HttpServletRequest httpRequest, @RequestBody RoleEntity role) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        role.setId(IdGenerator.generateId());
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        
        roleService.addRole(role);
        
        return role;
    }

    @RequestMapping(value = "api/updateRole", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public RoleEntity updateRole(HttpServletRequest httpRequest, @RequestBody RoleEntity role) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        
        roleService.updateRole(role);
        
        return role;
    }
}