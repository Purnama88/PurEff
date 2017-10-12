/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.Date;
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
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnPurchaseDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ReturnPurchaseDraftService;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.ItemReturnPurchaseDraftService;
import net.purnama.pureff.service.ItemReturnPurchaseService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.RateService;
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
public class ReturnPurchaseDraftController {
    
    @Autowired
    ReturnPurchaseDraftService returnpurchasedraftService;
    
    @Autowired
    ItemReturnPurchaseDraftService itemreturnpurchasedraftService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @Autowired
    ItemReturnPurchaseService itemreturnpurchaseService;
    
    @Autowired
    PartnerService partnerService;
    
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
    
    @RequestMapping(value = "api/getReturnPurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getReturnPurchaseDraftList() {
        
        List<ReturnPurchaseDraftEntity> ls = returnpurchasedraftService.getReturnPurchaseDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getReturnPurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnPurchaseDraft(@RequestParam(value="id") String id){
        return ResponseEntity.ok(returnpurchasedraftService.getReturnPurchaseDraft(id));
    }
    
    @RequestMapping(value = "api/addReturnPurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addReturnPurchaseDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        MenuEntity menu = menuService.getMenu(19);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu return purchase");
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
        
        ReturnPurchaseDraftEntity returnpurchasedraft = new ReturnPurchaseDraftEntity();
        returnpurchasedraft.setId(IdGenerator.generateId());
        returnpurchasedraft.setDate(date);
        returnpurchasedraft.setDuedate(date);
        returnpurchasedraft.setWarehouse(warehouse);
        returnpurchasedraft.setNumbering(numbering);
        returnpurchasedraft.setNote("");
        returnpurchasedraft.setSubtotal(0);
        returnpurchasedraft.setDiscount(0);
        returnpurchasedraft.setRounding(0);
        returnpurchasedraft.setFreight(0);
        returnpurchasedraft.setTax(0);
        returnpurchasedraft.setLastmodified(date);
        returnpurchasedraft.setLastmodifiedby(user);
        returnpurchasedraft.setCurrency(currency);
        if(rate == null){
            returnpurchasedraft.setRate(1);
        }
        else{
            returnpurchasedraft.setRate(rate.getValue());
        }
        
        returnpurchasedraftService.addReturnPurchaseDraft(returnpurchasedraft);
        
        return ResponseEntity.ok(returnpurchasedraft.getId());
    }

    @RequestMapping(value = "api/closeReturnPurchaseDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeReturnPurchaseDraft(@RequestParam(value="id") String id) {
        ReturnPurchaseDraftEntity returnpurchasedraft = returnpurchasedraftService.getReturnPurchaseDraft(id);
        
        NumberingEntity numbering = returnpurchasedraft.getNumbering();
        
        List<ItemReturnPurchaseDraftEntity> iisdelist = itemreturnpurchasedraftService.
                getItemReturnPurchaseDraftList(returnpurchasedraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(returnpurchasedraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(returnpurchasedraft.getCurrency() == null){
            return ResponseEntity.badRequest().body("Currency is not valid");
        }
        else if(returnpurchasedraft.getPartner() == null){
            return ResponseEntity.badRequest().body("Partner is not valid");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        else if(returnpurchasedraft.getDuedate().getTime().getDate() < returnpurchasedraft.getDate().getTime().getDate()){
            return ResponseEntity.badRequest().body("Due date is set before invoice date");
        }
        else if(returnpurchasedraft.getPartner().getMaximumdiscount() >= 0){
            if(returnpurchasedraft.getPartner().getMaximumdiscount() < returnpurchasedraft.getDiscount_percentage()){
                return ResponseEntity.badRequest().body("Discount is exceeding partner's maximum discount");
            }
        }
        else if(returnpurchasedraft.getLastmodifiedby().getDiscount() >= 0){
            if(returnpurchasedraft.getLastmodifiedby().getDiscount() < returnpurchasedraft.getDiscount_percentage()){
                return ResponseEntity.badRequest().body("You are not allowed to give such discount");
            }
        }
        
        if(!returnpurchasedraft.getLastmodifiedby().isDatebackward()){
            if(returnpurchasedraft.getDate().getTime().getDate() < new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date backward");
            }
        }
        
        if(!returnpurchasedraft.getLastmodifiedby().isDateforward()){
            if(returnpurchasedraft.getDate().getTime().getDate() > new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date forward");
            }
        }
        
        ReturnPurchaseEntity returnpurchase = new ReturnPurchaseEntity();
        returnpurchase.setId(IdGenerator.generateId());
        returnpurchase.setNumber(numbering.getCurrentId());
        returnpurchase.setDate(returnpurchasedraft.getDate());
        returnpurchase.setPrinted(0);
        returnpurchase.setWarehouse(returnpurchasedraft.getWarehouse());
        returnpurchase.setDraftid(returnpurchasedraft.getId());
        returnpurchase.setDuedate(returnpurchasedraft.getDuedate());
        returnpurchase.setSubtotal(returnpurchasedraft.getSubtotal());
        returnpurchase.setDiscount(returnpurchasedraft.getDiscount());
        returnpurchase.setRounding(returnpurchasedraft.getRounding());
        returnpurchase.setFreight(returnpurchasedraft.getFreight());
        returnpurchase.setTax(returnpurchasedraft.getTax());
        returnpurchase.setRate(returnpurchasedraft.getRate());
        returnpurchase.setPartner(returnpurchasedraft.getPartner());
        returnpurchase.setCurrency(returnpurchasedraft.getCurrency());
        returnpurchase.setCurrency_code(returnpurchasedraft.getCurrency().getCode());
        returnpurchase.setCurrency_name(returnpurchasedraft.getCurrency().getName());
        returnpurchase.setPartner_name(returnpurchasedraft.getPartner().getName());
        returnpurchase.setPartner_code(returnpurchasedraft.getPartner().getCode());
        returnpurchase.setPartner_address(returnpurchasedraft.getPartner().getAddress());
        returnpurchase.setNote(returnpurchasedraft.getNote());
        returnpurchase.setLastmodified(Calendar.getInstance());
        returnpurchase.setLastmodifiedby(returnpurchasedraft.getLastmodifiedby());
        returnpurchase.setStatus(true);
        returnpurchase.setUser_code(returnpurchase.getLastmodifiedby().getCode());
        returnpurchase.setWarehouse_code(returnpurchase.getWarehouse().getCode());
        returnpurchase.setPaid(0);
        
//        if(returnpurchase.getTotal_defaultcurrency() + 
//                returnpurchase.getPartner().getBalance() > 
//                returnpurchase.getPartner().getMaximumbalance()){
//            return ResponseEntity.badRequest().body("Cannot closed this invoice because it's exceeding partner's maximum balance");
//        }
        
        returnpurchaseService.addReturnPurchase(returnpurchase);
        
        for(ItemReturnPurchaseDraftEntity iisde : iisdelist){
            ItemReturnPurchaseEntity iise = new ItemReturnPurchaseEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setPrice(iisde.getPrice());
            iise.setDiscount(iisde.getDiscount());
            iise.setCost(iisde.getItem().getCost());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoice_ref((iisde.getInvoice_ref()));
            iise.setReturnpurchase(returnpurchase);
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
            itemreturnpurchaseService.addItemReturnPurchase(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(returnpurchase.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() - iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        ReturnPurchaseEntity tempreturnpurchase = returnpurchaseService.getReturnPurchase(returnpurchase.getId());
        
        PartnerEntity partner = returnpurchase.getPartner();
        partner.setBalance(partner.getBalance() - tempreturnpurchase.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemReturnPurchaseDraftEntity idd : itemreturnpurchasedraftService.getItemReturnPurchaseDraftList(returnpurchasedraft)){
            itemreturnpurchasedraftService.deleteItemReturnPurchaseDraft(idd.getId());
        }
        
        returnpurchasedraftService.deleteReturnPurchaseDraft(id);
        
        return ResponseEntity.ok(returnpurchase.getId());
    }
    
    @RequestMapping(value = "api/updateReturnPurchaseDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateReturnPurchaseDraft(HttpServletRequest httpRequest, @RequestBody ReturnPurchaseDraftEntity returnpurchasedraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        returnpurchasedraft.setLastmodified(Calendar.getInstance());
        returnpurchasedraft.setWarehouse(warehouse);
        returnpurchasedraft.setLastmodifiedby(user);
        
        returnpurchasedraftService.updateReturnPurchaseDraft(returnpurchasedraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteReturnPurchaseDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteReturnPurchaseDraft(@RequestParam(value="id") String id) {
        ReturnPurchaseDraftEntity returnpurchasedraft = returnpurchasedraftService.getReturnPurchaseDraft(id);
        
        for(ItemReturnPurchaseDraftEntity idd : itemreturnpurchasedraftService.getItemReturnPurchaseDraftList(returnpurchasedraft)){
            itemreturnpurchasedraftService.deleteItemReturnPurchaseDraft(idd.getId());
        }
        
        returnpurchasedraftService.deleteReturnPurchaseDraft(id);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getReturnPurchaseDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getReturnPurchaseDraftList(
            HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        List<ReturnPurchaseDraftEntity> ls = returnpurchasedraftService.
                getReturnPurchaseDraftList(itemperpage, page, sort, keyword, user, warehouse);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countReturnPurchaseDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countReturnPurchaseDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        return ResponseEntity.ok(returnpurchasedraftService.countReturnPurchaseDraftList(keyword, user, warehouse));
    }
}
