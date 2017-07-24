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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addCurrency(HttpServletRequest request, @RequestBody CurrencyEntity currency){
        
        String header = request.getHeader("UserId");
        
        UserEntity user = userService.getUser(header);
        
        currency.setDefaultcurrency(true);
        currency.setNote("");
        currency.setStatus(true);
        currency.setId(IdGenerator.generateId());
        currency.setLastmodified(Calendar.getInstance());
        currency.setLastmodifiedby(user);
        
        currencyService.addCurrency(currency);
        
        return ResponseEntity.ok(currency);
    }
    
    @RequestMapping(value = "/init/addUser", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user){
        
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
//        role.setAdjustment_read(true);
//        role.setAdjustment_write(true);
//        role.setCurrency_read(true);
//        role.setCurrency_write(true);
//        role.setDelivery_read(true);
//        role.setDelivery_write(true);
//        role.setExpenses_read(true);
//        role.setExpenses_write(true);
//        role.setInvoicepurchase_read(true);
//        role.setInvoicepurchase_write(true);
//        role.setInvoicesales_read(true);
//        role.setInvoicesales_write(true);
//        role.setInvoicewarehousein_read(true);
//        role.setInvoicewarehousein_write(true);
//        role.setInvoicewarehouseout_read(true);
//        role.setInvoicewarehouseout_write(true);
//        role.setItem_read(true);
//        role.setItem_write(true);
//        role.setItemgroup_read(true);
//        role.setItemgroup_write(true);
//        role.setNumbering_read(true);
//        role.setNumbering_write(true);
//        role.setNumberingname_read(true);
//        role.setNumberingname_write(true);
//        role.setPartner_read(true);
//        role.setPartner_write(true);
//        role.setPartnertype_read(true);
//        role.setPartnertype_write(true);
//        role.setPaymentin_read(true);
//        role.setPaymentin_write(true);
//        role.setPaymentout_read(true);
//        role.setPaymentout_write(true);
//        role.setPaymenttype_read(true);
//        role.setPaymenttype_write(true);
//        role.setReturnpurchase_read(true);
//        role.setReturnpurchase_write(true);
//        role.setReturnsales_read(true);
//        role.setReturnsales_write(true);
//        role.setRole_read(true);
//        role.setRole_write(true);
//        role.setUom_read(true);
//        role.setUom_write(true);
//        role.setUser_read(true);
//        role.setUser_write(true);
//        role.setWarehouse_read(true);
//        role.setWarehouse_write(true);
        role.setStatus(true);
        roleService.addRole(role);
        
        user.setRole(role);
        userService.updateUser(user);
        
        return ResponseEntity.ok(user.getId());
    }
    
    @RequestMapping(value = "/init/addWarehouse", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public ResponseEntity<?> addWarehouse(HttpServletRequest request, @RequestBody WarehouseEntity warehouse){
        
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
        
        return ResponseEntity.ok(warehouse);
    }
    
    @RequestMapping(value = "/init/addUom", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public ResponseEntity<?> addUom(HttpServletRequest request, @RequestBody UomEntity uom){
        
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
        
        return ResponseEntity.ok(uom);
    }
}
