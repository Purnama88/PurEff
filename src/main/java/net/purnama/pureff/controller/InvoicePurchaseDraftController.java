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
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.RateEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoicePurchaseDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.InvoicePurchaseDraftService;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.ItemInvoicePurchaseDraftService;
import net.purnama.pureff.service.ItemInvoicePurchaseService;
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
public class InvoicePurchaseDraftController {
    
    @Autowired
    InvoicePurchaseDraftService invoicepurchasedraftService;
    
    @Autowired
    ItemInvoicePurchaseDraftService iteminvoicepurchasedraftService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @Autowired
    ItemInvoicePurchaseService iteminvoicepurchaseService;
    
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
    
    @RequestMapping(value = "api/getInvoicePurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoicePurchaseDraftList() {
        
        List<InvoicePurchaseDraftEntity> ls = invoicepurchasedraftService.getInvoicePurchaseDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoicePurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoicePurchaseDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicepurchasedraftService.getInvoicePurchaseDraft(id));
    }
    
    @RequestMapping(value = "api/addInvoicePurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addInvoicePurchaseDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(7);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu invoice purchase");
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
        
        InvoicePurchaseDraftEntity invoicepurchasedraft = new InvoicePurchaseDraftEntity();
        invoicepurchasedraft.setId(IdGenerator.generateId());
        invoicepurchasedraft.setDate(date);
        invoicepurchasedraft.setDuedate(date);
        invoicepurchasedraft.setWarehouse(warehouse);
        invoicepurchasedraft.setNumbering(numbering);
        invoicepurchasedraft.setNote("");
        invoicepurchasedraft.setSubtotal(0);
        invoicepurchasedraft.setDiscount(0);
        invoicepurchasedraft.setRounding(0);
        invoicepurchasedraft.setFreight(0);
        invoicepurchasedraft.setTax(0);
        invoicepurchasedraft.setLastmodified(date);
        invoicepurchasedraft.setLastmodifiedby(user);
        invoicepurchasedraft.setCurrency(currency);
        if(rate == null){
            invoicepurchasedraft.setRate(1);
        }
        else{
            invoicepurchasedraft.setRate(rate.getValue());
        }
        
        invoicepurchasedraftService.addInvoicePurchaseDraft(invoicepurchasedraft);
        
        return ResponseEntity.ok(invoicepurchasedraft.getId());
    }

    @RequestMapping(value = "api/closeInvoicePurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoicePurchaseDraft(@RequestParam(value="id") String id) {
        InvoicePurchaseDraftEntity invoicepurchasedraft = invoicepurchasedraftService.getInvoicePurchaseDraft(id);
        
        NumberingEntity numbering = invoicepurchasedraft.getNumbering();
        
        List<ItemInvoicePurchaseDraftEntity> iisdelist = iteminvoicepurchasedraftService.
                getItemInvoicePurchaseDraftList(invoicepurchasedraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(invoicepurchasedraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(invoicepurchasedraft.getCurrency() == null){
            return ResponseEntity.badRequest().body("Currency is not valid");
        }
        else if(invoicepurchasedraft.getPartner() == null){
            return ResponseEntity.badRequest().body("Partner is not valid");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        InvoicePurchaseEntity invoicepurchase = new InvoicePurchaseEntity();
        invoicepurchase.setId(IdGenerator.generateId());
        invoicepurchase.setNumber(numbering.getCurrentId());
        invoicepurchase.setDate(invoicepurchasedraft.getDate());
        invoicepurchase.setPrinted(0);
        invoicepurchase.setWarehouse(invoicepurchasedraft.getWarehouse());
        invoicepurchase.setDraftid(invoicepurchasedraft.getId());
        invoicepurchase.setDuedate(invoicepurchasedraft.getDuedate());
        invoicepurchase.setSubtotal(invoicepurchasedraft.getSubtotal());
        invoicepurchase.setDiscount(invoicepurchasedraft.getDiscount());
        invoicepurchase.setRounding(invoicepurchasedraft.getRounding());
        invoicepurchase.setFreight(invoicepurchasedraft.getFreight());
        invoicepurchase.setTax(invoicepurchasedraft.getTax());
        invoicepurchase.setRate(invoicepurchasedraft.getRate());
        invoicepurchase.setPartner(invoicepurchasedraft.getPartner());
        invoicepurchase.setCurrency(invoicepurchasedraft.getCurrency());
        invoicepurchase.setCurrency_code(invoicepurchasedraft.getCurrency().getCode());
        invoicepurchase.setCurrency_name(invoicepurchasedraft.getCurrency().getName());
        invoicepurchase.setPartner_name(invoicepurchasedraft.getPartner().getName());
        invoicepurchase.setPartner_code(invoicepurchasedraft.getPartner().getCode());
        invoicepurchase.setPartner_address(invoicepurchasedraft.getPartner().getAddress());
        invoicepurchase.setNote(invoicepurchasedraft.getNote());
        invoicepurchase.setLastmodified(Calendar.getInstance());
        invoicepurchase.setLastmodifiedby(invoicepurchasedraft.getLastmodifiedby());
        invoicepurchase.setStatus(true);
        invoicepurchase.setUser_code(invoicepurchase.getLastmodifiedby().getCode());
        invoicepurchase.setWarehouse_code(invoicepurchase.getWarehouse().getCode());
        invoicepurchase.setPaid(0);
        invoicepurchaseService.addInvoicePurchase(invoicepurchase);
        
        for(ItemInvoicePurchaseDraftEntity iisde : iisdelist){
            ItemInvoicePurchaseEntity iise = new ItemInvoicePurchaseEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setPrice(iisde.getPrice());
            iise.setDiscount(iisde.getDiscount());
            iise.setCost(iisde.getItem().getCost());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoicepurchase(invoicepurchase);
            iise.setItem_name(iisde.getItem().getName());
            iise.setItem_code(iisde.getItem().getCode());
            iise.setUom_name(iisde.getUom().getName());
            if(iisde.getUom().getParent() != null){
                iise.setBaseuom_name(iisde.getUom().getParent().getName());
                iise.setBasequantity(iisde.getUom().getValue() * iisde.getQuantity());
            }
            else{
                iise.setBaseuom_name(iisde.getUom().getName());
                iise.setBasequantity(iisde.getQuantity());
            }
            iteminvoicepurchaseService.addItemInvoicePurchase(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(invoicepurchase.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() + iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        PartnerEntity partner = invoicepurchase.getPartner();
        partner.setBalance(partner.getBalance() + invoicepurchase.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemInvoicePurchaseDraftEntity idd : iteminvoicepurchasedraftService.getItemInvoicePurchaseDraftList(invoicepurchasedraft)){
            iteminvoicepurchasedraftService.deleteItemInvoicePurchaseDraft(idd.getId());
        }
        
        invoicepurchasedraftService.deleteInvoicePurchaseDraft(id);
        
        return ResponseEntity.ok(invoicepurchase.getId());
    }
    
    @RequestMapping(value = "api/updateInvoicePurchaseDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoicePurchaseDraft(HttpServletRequest httpRequest,
            @RequestBody InvoicePurchaseDraftEntity invoicepurchasedraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicepurchasedraft.setLastmodified(Calendar.getInstance());
        invoicepurchasedraft.setWarehouse(warehouse);
        invoicepurchasedraft.setLastmodifiedby(user);
        
        invoicepurchasedraftService.updateInvoicePurchaseDraft(invoicepurchasedraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteInvoicePurchaseDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteInvoicePurchaseDraft(@RequestParam(value="id") String id){
        InvoicePurchaseDraftEntity invoicepurchasedraft = invoicepurchasedraftService.getInvoicePurchaseDraft(id);
        
        for(ItemInvoicePurchaseDraftEntity idd : iteminvoicepurchasedraftService.getItemInvoicePurchaseDraftList(invoicepurchasedraft)){
            iteminvoicepurchasedraftService.deleteItemInvoicePurchaseDraft(idd.getId());
        }
        
        invoicepurchasedraftService.deleteInvoicePurchaseDraft(id);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoicePurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoicePurchaseDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<InvoicePurchaseDraftEntity> ls = invoicepurchasedraftService.
                getInvoicePurchaseDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoicePurchaseDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoicePurchaseDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return ResponseEntity.ok(invoicepurchasedraftService.countInvoicePurchaseDraftList(keyword, user));
    }
}
