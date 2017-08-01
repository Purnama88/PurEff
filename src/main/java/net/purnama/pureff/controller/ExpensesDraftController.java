/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.RateEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemExpensesDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ExpensesDraftService;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.ItemExpensesDraftService;
import net.purnama.pureff.service.ItemExpensesService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.RateService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
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
public class ExpensesDraftController {
    
    @Autowired
    ExpensesDraftService expensesdraftService;
    
    @Autowired
    ItemExpensesDraftService itemexpensesdraftService;
    
    @Autowired
    ExpensesService expensesService;
    
    @Autowired
    ItemExpensesService itemexpensesService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    RateService rateService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    NumberingService numberingService;
    
    @RequestMapping(value = "api/getExpensesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getExpensesDraftList() {
        List<ExpensesDraftEntity> ls = expensesdraftService.getExpensesDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getExpensesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getExpensesDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(expensesdraftService.getExpensesDraft(id));
    }
    
    @RequestMapping(value = "api/addExpensesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addExpensesDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(4);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu expenses");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        CurrencyEntity currency = currencyService.getDefaultCurrency();
        RateEntity rate = rateService.getLastRate(currency);
        Calendar date = Calendar.getInstance();
        
        ExpensesDraftEntity expensesdraft = new ExpensesDraftEntity();
        expensesdraft.setId(IdGenerator.generateId());
        expensesdraft.setDate(date);
        expensesdraft.setDuedate(date);
        expensesdraft.setWarehouse(warehouse);
        expensesdraft.setNumbering(numbering);
        expensesdraft.setNote("");
        expensesdraft.setSubtotal(0);
        expensesdraft.setDiscount(0);
        expensesdraft.setRounding(0);
        expensesdraft.setFreight(0);
        expensesdraft.setTax(0);
        expensesdraft.setLastmodified(date);
        expensesdraft.setLastmodifiedby(user);
        expensesdraft.setCurrency(currency);
        if(rate == null){
            expensesdraft.setRate(1);
        }
        else{
            expensesdraft.setRate(rate.getValue());
        }
        
        expensesdraftService.addExpensesDraft(expensesdraft);
        
        return ResponseEntity.ok(expensesdraft.getId());
    }

    @RequestMapping(value = "api/closeExpensesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeExpensesDraft(@RequestParam(value="id") String id) {
        ExpensesDraftEntity expensesdraft = expensesdraftService.getExpensesDraft(id);
        
        NumberingEntity numbering = expensesdraft.getNumbering();
        
        List<ItemExpensesDraftEntity> iisdelist = itemexpensesdraftService.
                getItemExpensesDraftList(expensesdraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(expensesdraft.getCurrency() == null){
            return ResponseEntity.badRequest().body("Currency is not valid");
        }
        else if(expensesdraft.getPartner() == null){
            return ResponseEntity.badRequest().body("Partner is not valid");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        ExpensesEntity expenses = new ExpensesEntity();
        expenses.setId(IdGenerator.generateId());
        expenses.setNumber(numbering.getCurrentId());
        expenses.setDate(expensesdraft.getDate());
        expenses.setPrinted(0);
        expenses.setWarehouse(expensesdraft.getWarehouse());
        expenses.setDraftid(expensesdraft.getId());
        expenses.setDuedate(expensesdraft.getDuedate());
        expenses.setSubtotal(expensesdraft.getSubtotal());
        expenses.setDiscount(expensesdraft.getDiscount());
        expenses.setRounding(expensesdraft.getRounding());
        expenses.setFreight(expensesdraft.getFreight());
        expenses.setTax(expensesdraft.getTax());
        expenses.setRate(expensesdraft.getRate());
        expenses.setPartner(expensesdraft.getPartner());
        expenses.setCurrency(expensesdraft.getCurrency());
        expenses.setCurrency_code(expensesdraft.getCurrency().getCode());
        expenses.setCurrency_name(expensesdraft.getCurrency().getName());
        expenses.setPartner_name(expensesdraft.getPartner().getName());
        expenses.setPartner_code(expensesdraft.getPartner().getCode());
        expenses.setPartner_address(expensesdraft.getPartner().getAddress());
        expenses.setNote(expensesdraft.getNote());
        expenses.setLastmodified(Calendar.getInstance());
        expenses.setLastmodifiedby(expensesdraft.getLastmodifiedby());
        expenses.setStatus(true);
        expenses.setUser_code(expenses.getLastmodifiedby().getCode());
        expenses.setWarehouse_code(expenses.getWarehouse().getCode());
        expenses.setPaid(0);
        expensesService.addExpenses(expenses);
        
        for(ItemExpensesDraftEntity iisde : iisdelist){
            ItemExpensesEntity iise = new ItemExpensesEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setPrice(iisde.getPrice());
            iise.setDiscount(iisde.getDiscount());
            iise.setDescription(iisde.getDescription());
            iise.setExpenses(expenses);
            
            itemexpensesService.addItemExpenses(iise);
        }
        
        PartnerEntity partner = expenses.getPartner();
        partner.setBalance(partner.getBalance() + expenses.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemExpensesDraftEntity idd : itemexpensesdraftService.getItemExpensesDraftList(expensesdraft)){
            itemexpensesdraftService.deleteItemExpensesDraft(idd.getId());
        }
        
        expensesdraftService.deleteExpensesDraft(id);
        
        return ResponseEntity.ok(expenses.getId());
    }
    
    @RequestMapping(value = "api/updateExpensesDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateExpensesDraft(HttpServletRequest httpRequest, 
            @RequestBody ExpensesDraftEntity expensesdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        expensesdraft.setLastmodified(Calendar.getInstance());
        expensesdraft.setWarehouse(warehouse);
        expensesdraft.setLastmodifiedby(user);
        
        expensesdraftService.updateExpensesDraft(expensesdraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteExpensesDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteExpensesDraft(@RequestParam(value="id") String id) {
        ExpensesDraftEntity expensesdraft = expensesdraftService.getExpensesDraft(id);
        
        for(ItemExpensesDraftEntity idd : itemexpensesdraftService.getItemExpensesDraftList(expensesdraft)){
            itemexpensesdraftService.deleteItemExpensesDraft(idd.getId());
        }
        
        expensesdraftService.deleteExpensesDraft(id);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getExpensesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getExpensesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<ExpensesDraftEntity> ls = expensesdraftService.
                getExpensesDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countExpensesDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countExpensesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return ResponseEntity.ok(expensesdraftService.countExpensesDraftList(keyword, user));
    }
}
