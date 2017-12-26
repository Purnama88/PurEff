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
        
        if(!currencyService.getCurrencyList().isEmpty()){
            return ResponseEntity.badRequest().body("This database is not empty. You need an empty database to initialize the system.");
        }
        
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
        
        if(!userService.getUserList().isEmpty()){
            return ResponseEntity.badRequest().body("This database is not empty. You need an empty database to initialize the system.");
        }
        
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
        
        try{
            String password = user.getPassword();
            user.setPassword(net.purnama.pureff.util.GlobalFunctions.encrypt(password));
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("");
        }
        
        userService.addUser(user);
        
        RoleEntity role = new RoleEntity();
        role.setId(IdGenerator.generateId());
        role.setName("SUPERADMIN");
        role.setDefaultrole(true);
        role.setLastmodified(Calendar.getInstance());
        role.setLastmodifiedby(user);
        role.setNote("");
        role.setStatus(true);
        
        role.setUser_home(true);
        role.setUser_add(true);
        role.setUser_edit(true);
        role.setUser_detail(true);

        role.setRole_home(true);
        role.setRole_add(true);
        role.setRole_edit(true);

        role.setMenu_home(true);
        role.setMenu_detail(true);

        role.setNumbering_add(true);
        role.setNumbering_edit(true);

        role.setCurrency_home(true);
        role.setCurrency_add(true);
        role.setCurrency_edit(true);
        role.setCurrency_detail(true);

        role.setRate_add(true);
        role.setRate_detail(true);

        role.setWarehouse_home(true);
        role.setWarehouse_add(true);
        role.setWarehouse_edit(true);

        role.setNumberingname_home(true);
        role.setNumberingname_add(true);
        role.setNumberingname_edit(true);

        role.setPartnertype_home(true);
        role.setPartnertype_add(true);
        role.setPartnertype_edit(true);

        role.setPartner_home(true);
        role.setPartner_add(true);
        role.setPartner_edit(true);
        role.setPartner_detail(true);

        role.setUom_home(true);
        role.setUom_add(true);
        role.setUom_edit(true);

        role.setItemgroup_home(true);
        role.setItemgroup_add(true);
        role.setItemgroup_edit(true);

        role.setItem_home(true);
        role.setItem_add(true);
        role.setItem_edit(true);
        role.setItem_detail(true);

        role.setBuyprice_edit(true);

        role.setSellprice_edit(true);

        role.setInvoicesales_home(true);
        role.setInvoicesales_draft(true);
        role.setInvoicesales_cancel(true);
        role.setInvoicesales_close(true);

        role.setReturnsales_home(true);
        role.setReturnsales_draft(true);
        role.setReturnsales_cancel(true);
        role.setReturnsales_close(true);

        role.setDelivery_home(true);
        role.setDelivery_draft(true);
        role.setDelivery_cancel(true);
        role.setDelivery_close(true);

        role.setInvoicepurchase_home(true);
        role.setInvoicepurchase_draft(true);
        role.setInvoicepurchase_cancel(true);
        role.setInvoicepurchase_close(true);

        role.setReturnpurchase_home(true);
        role.setReturnpurchase_draft(true);
        role.setReturnpurchase_cancel(true);
        role.setReturnpurchase_close(true);

        role.setExpenses_home(true);
        role.setExpenses_draft(true);
        role.setExpenses_cancel(true);
        role.setExpenses_close(true);

        role.setAdjustment_home(true);
        role.setAdjustment_draft(true);
        role.setAdjustment_cancel(true);
        role.setAdjustment_close(true);

        role.setInvoicewarehouseout_home(true);
        role.setInvoicewarehouseout_draft(true);
        role.setInvoicewarehouseout_cancel(true);
        role.setInvoicewarehouseout_close(true);

        role.setInvoicewarehousein_home(true);
        role.setInvoicewarehousein_draft(true);
        role.setInvoicewarehousein_cancel(true);
        role.setInvoicewarehousein_close(true);

        role.setPaymentout_home(true);
        role.setPaymentout_draft(true);
        role.setPaymentout_cancel(true);
        role.setPaymentout_close(true);

        role.setPaymentin_home(true);
        role.setPaymentin_draft(true);
        role.setPaymentin_cancel(true);
        role.setPaymentin_close(true);

        role.setPaymenttypein_home(true);
        role.setPaymenttypein_invalidate(true);

        role.setPaymenttypeout_home(true);
        role.setPaymenttypeout_invalidate(true);
        
        roleService.addRole(role);
        
        user.setRole(role);
        userService.updateUser(user);
        
        return ResponseEntity.ok(user.getId());
    }
    
    @RequestMapping(value = "/init/addWarehouse", method = RequestMethod.POST, 
            headers = "Accept=application/json")
    public ResponseEntity<?> addWarehouse(HttpServletRequest request, @RequestBody WarehouseEntity warehouse){
        
        if(!warehouseService.getWarehouseList().isEmpty()){
            return ResponseEntity.badRequest().body("This database is not empty. You need an empty database to initialize the system.");
        }
        
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
        
        if(!uomService.getUomList().isEmpty()){
            return ResponseEntity.badRequest().body("This database is not empty. You need an empty database to initialize the system.");
        }
        
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
