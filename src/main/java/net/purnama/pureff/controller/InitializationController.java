/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.RoleEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.RoleService;
import net.purnama.pureff.service.UomService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class InitializationController {
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    UomService uomService;
    
    @Autowired
    MenuService menuService;
    
    @RequestMapping(value = "/init/addCurrency", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public CurrencyEntity addCurrency(HttpServletRequest request, @RequestBody CurrencyEntity currency){
        
        String header = request.getHeader("UserId");
        
        UserEntity user = userService.getUser(header);
        
        currency.setDefaultcurrency(true);
        currency.setNote("");
        currency.setStatus(true);
        currency.setId(IdGenerator.generateId());
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(user);
        
        currencyService.addCurrency(currency);
        
        return currency;
    }
    
    @RequestMapping(value = "/init/addUser", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public String addUser(@RequestBody UserEntity user){
        
        user.setId(IdGenerator.generateId());
        user.setCode(user.getId());
        user.setDatebackward(true);
        user.setDateforward(true);
        user.setDiscount(-1);
        user.setLastmodified(Calendar.getInstance());
        user.setLower_buyprice(true);
        user.setLower_sellprice(true);
        user.setNote("");
        user.setRaise_buyprice(true);
        user.setRaise_sellprice(true);
        user.setStatus(true);
        user.setLastmodifiedby(user);
        userService.addUser(user);
        
        RoleEntity role = new RoleEntity();
        role.setId(IdGenerator.generateId());
        role.setName("SUPERADMIN");
        role.setDefaultrole(true);
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        role.setNote("");
        role.setStatus(true);
        roleService.addRole(role);
        
        user.setRole(role);
        userService.updateUser(user);
        
        return user.getId();
    }
    
    @RequestMapping(value = "/init/addWarehouse", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public WarehouseEntity addWarehouse(HttpServletRequest request, @RequestBody WarehouseEntity warehouse){
        
        String header = request.getHeader("UserId");
        
        UserEntity user = userService.getUser(header);
        
        warehouse.setId(IdGenerator.generateId());
        warehouse.setDefaultwarehouse(false);
        warehouse.setNote("");
        warehouse.setPort("");
        warehouse.setStatus(true);
        warehouse.setUrl("");
        warehouse.setLastmodified(Calendar.getInstance());
        warehouse.setLastmodifiedby(user);
        warehouseService.addWarehouse(warehouse);
        
        Set<WarehouseEntity> set = new HashSet<>();
        set.add(warehouse);
        
        user.setWarehouses(set);
        userService.updateUser(user);
        
        return warehouse;
    }
    
    @RequestMapping(value = "/init/addUom", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public UomEntity addUom(HttpServletRequest request, @RequestBody UomEntity uom){
        
        String header = request.getHeader("UserId");
        
        UserEntity user = userService.getUser(header);
        
        uom.setId(IdGenerator.generateId());
        uom.setNote("");
        uom.setParent(null);
        uom.setStatus(true);
        uom.setLastmodified(Calendar.getInstance());
        uom.setLastmodifiedby(user);
        uomService.addUom(uom);
        
        for(int i = 0; i < MenuEntity.MENU_NAMES.length; i++){
            MenuEntity menu = new MenuEntity();
            menu.setId(i+1);
            menu.setName(MenuEntity.MENU_NAMES[i]);
            menu.setTransactional(MenuEntity.MENU_TRANSACTION[i]);
            menuService.addMenu(menu);
        }
        
        return uom;
    }
}
