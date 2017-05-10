/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class MenuController {
    
    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "api/getMenuList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?>  getMenuList() {
        
        List<MenuEntity> ls = menuService.getMenuList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getTransactionalMenuList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?>  getTransactionalMenuList() {
        
        List<MenuEntity> ls = menuService.getTransactionalMenuList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getMenu", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getMenu(@RequestParam(value="id") int id) {
        return ResponseEntity.ok(menuService.getMenu(id));
    }
}
