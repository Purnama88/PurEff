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
public class MenuController {
    
    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "api/getMenuList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<MenuEntity> getMenuList() {
        
        List<MenuEntity> ls = menuService.getMenuList();
        return ls;
    }
    
    @RequestMapping(value = "api/getTransactionalMenuList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<MenuEntity> getTransactionalMenuList() {
        
        List<MenuEntity> ls = menuService.getTransactionalMenuList();
        return ls;
    }
    
    @RequestMapping(value = "api/getMenu/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public MenuEntity getMenu(@PathVariable int id) {
        return menuService.getMenu(id);
    }

    @RequestMapping(value = "api/addMenu", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public MenuEntity addMenu(@RequestBody MenuEntity menu) {
        
        menuService.addMenu(menu);
        
        return menu;
    }

    @RequestMapping(value = "api/updateMenu", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public MenuEntity updateMenu(@RequestBody MenuEntity menu) {
        menuService.updateMenu(menu);
        
        return menu;
    }
}
