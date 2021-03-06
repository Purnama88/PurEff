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
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnSalesDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ReturnSalesDraftService;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.service.ItemReturnSalesDraftService;
import net.purnama.pureff.service.ItemReturnSalesService;
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
public class ReturnSalesDraftController {
    
    @Autowired
    ReturnSalesDraftService returnsalesdraftService;
    
    @Autowired
    ItemReturnSalesDraftService itemreturnsalesdraftService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @Autowired
    ItemReturnSalesService itemreturnsalesService;
    
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
    
    @RequestMapping(value = "api/getReturnSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getReturnSalesDraftList() {
        
        List<ReturnSalesDraftEntity> ls = returnsalesdraftService.getReturnSalesDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getReturnSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnSalesDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(returnsalesdraftService.getReturnSalesDraft(id));
    }
    
    @RequestMapping(value = "api/addReturnSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addReturnSalesDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(20);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu return sales");
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
        
        ReturnSalesDraftEntity returnsalesdraft = new ReturnSalesDraftEntity();
        returnsalesdraft.setId(IdGenerator.generateId());
        returnsalesdraft.setDate(date);
        returnsalesdraft.setDuedate(date);
        returnsalesdraft.setWarehouse(warehouse);
        returnsalesdraft.setNumbering(numbering);
        returnsalesdraft.setNote("");
        returnsalesdraft.setSubtotal(0);
        returnsalesdraft.setDiscount(0);
        returnsalesdraft.setRounding(0);
        returnsalesdraft.setFreight(0);
        returnsalesdraft.setTax(0);
        returnsalesdraft.setLastmodified(date);
        returnsalesdraft.setLastmodifiedby(user);
        returnsalesdraft.setCurrency(currency);
        if(rate == null){
            returnsalesdraft.setRate(1);
        }
        else{
            returnsalesdraft.setRate(rate.getValue());
        }
        
        returnsalesdraftService.addReturnSalesDraft(returnsalesdraft);
        
        return ResponseEntity.ok(returnsalesdraft.getId());
    }

    @RequestMapping(value = "api/closeReturnSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeReturnSalesDraft(@RequestParam(value="id") String id) {
        ReturnSalesDraftEntity returnsalesdraft = returnsalesdraftService.getReturnSalesDraft(id);
        
        NumberingEntity numbering = returnsalesdraft.getNumbering();
        
        List<ItemReturnSalesDraftEntity> iisdelist = itemreturnsalesdraftService.
                getItemReturnSalesDraftList(returnsalesdraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(returnsalesdraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(returnsalesdraft.getCurrency() == null){
            return ResponseEntity.badRequest().body("Currency is not valid");
        }
        else if(returnsalesdraft.getPartner() == null){
            return ResponseEntity.badRequest().body("Partner is not valid");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        else if(returnsalesdraft.getDuedate().getTime().getDate() < returnsalesdraft.getDate().getTime().getDate()){
            return ResponseEntity.badRequest().body("Due date is set before invoice date");
        }
        else if(returnsalesdraft.getPartner().getMaximumdiscount() >= 0){
            if(returnsalesdraft.getPartner().getMaximumdiscount() < returnsalesdraft.getDiscount_percentage()){
                return ResponseEntity.badRequest().body("Discount is exceeding partner's maximum discount");
            }
        }
        else if(returnsalesdraft.getLastmodifiedby().getDiscount() >= 0){
            if(returnsalesdraft.getLastmodifiedby().getDiscount() < returnsalesdraft.getDiscount_percentage()){
                return ResponseEntity.badRequest().body("You are not allowed to give such discount");
            }
        }
        
        if(!returnsalesdraft.getLastmodifiedby().isDatebackward()){
            if(returnsalesdraft.getDate().getTime().getDate() < new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date backward");
            }
        }
        
        if(!returnsalesdraft.getLastmodifiedby().isDateforward()){
            if(returnsalesdraft.getDate().getTime().getDate() > new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date forward");
            }
        }
        
        ReturnSalesEntity returnsales = new ReturnSalesEntity();
        returnsales.setId(IdGenerator.generateId());
        returnsales.setNumber(numbering.getCurrentId());
        returnsales.setDate(returnsalesdraft.getDate());
        returnsales.setPrinted(0);
        returnsales.setWarehouse(returnsalesdraft.getWarehouse());
        returnsales.setDraftid(returnsalesdraft.getId());
        returnsales.setDuedate(returnsalesdraft.getDuedate());
        returnsales.setSubtotal(returnsalesdraft.getSubtotal());
        returnsales.setDiscount(returnsalesdraft.getDiscount());
        returnsales.setRounding(returnsalesdraft.getRounding());
        returnsales.setFreight(returnsalesdraft.getFreight());
        returnsales.setTax(returnsalesdraft.getTax());
        returnsales.setRate(returnsalesdraft.getRate());
        returnsales.setPartner(returnsalesdraft.getPartner());
        returnsales.setCurrency(returnsalesdraft.getCurrency());
        returnsales.setCurrency_code(returnsalesdraft.getCurrency().getCode());
        returnsales.setCurrency_name(returnsalesdraft.getCurrency().getName());
        returnsales.setPartner_name(returnsalesdraft.getPartner().getName());
        returnsales.setPartner_code(returnsalesdraft.getPartner().getCode());
        returnsales.setPartner_address(returnsalesdraft.getPartner().getAddress());
        returnsales.setNote(returnsalesdraft.getNote());
        returnsales.setLastmodified(Calendar.getInstance());
        returnsales.setLastmodifiedby(returnsalesdraft.getLastmodifiedby());
        returnsales.setStatus(true);
        returnsales.setUser_code(returnsales.getLastmodifiedby().getCode());
        returnsales.setWarehouse_code(returnsales.getWarehouse().getCode());
        returnsales.setPaid(0);
        
//        if(returnsales.getTotal_defaultcurrency() + 
//                returnsales.getPartner().getBalance() > 
//                returnsales.getPartner().getMaximumbalance()){
//            return ResponseEntity.badRequest().body("Cannot closed this invoice because it's exceeding partner's maximum balance");
//        }
        
        returnsalesService.addReturnSales(returnsales);
        
        for(ItemReturnSalesDraftEntity iisde : iisdelist){
            ItemReturnSalesEntity iise = new ItemReturnSalesEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setPrice(iisde.getPrice());
            iise.setDiscount(iisde.getDiscount());
            iise.setCost(iisde.getItem().getCost());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoice_ref((iisde.getInvoice_ref()));
            iise.setReturnsales(returnsales);
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
            itemreturnsalesService.addItemReturnSales(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(returnsales.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() + iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        ReturnSalesEntity tempreturnsales = returnsalesService.getReturnSales(returnsales.getId());
        
        PartnerEntity partner = returnsales.getPartner();
        partner.setBalance(partner.getBalance() - tempreturnsales.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemReturnSalesDraftEntity idd : itemreturnsalesdraftService.getItemReturnSalesDraftList(returnsalesdraft)){
            itemreturnsalesdraftService.deleteItemReturnSalesDraft(idd.getId());
        }
        
        returnsalesdraftService.deleteReturnSalesDraft(id);
        
        return ResponseEntity.ok(returnsales.getId());
    }
    
    @RequestMapping(value = "api/updateReturnSalesDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateReturnSalesDraft(HttpServletRequest httpRequest,
            @RequestBody ReturnSalesDraftEntity returnsalesdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        returnsalesdraft.setLastmodified(Calendar.getInstance());
        returnsalesdraft.setWarehouse(warehouse);
        returnsalesdraft.setLastmodifiedby(user);
        
        returnsalesdraftService.updateReturnSalesDraft(returnsalesdraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteReturnSalesDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteReturnSalesDraft(@RequestParam(value="id") String id) {
        ReturnSalesDraftEntity returnsalesdraft = returnsalesdraftService.getReturnSalesDraft(id);
        
        for(ItemReturnSalesDraftEntity idd : itemreturnsalesdraftService.getItemReturnSalesDraftList(returnsalesdraft)){
            itemreturnsalesdraftService.deleteItemReturnSalesDraft(idd.getId());
        }
        
        returnsalesdraftService.deleteReturnSalesDraft(id);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getReturnSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getReturnSalesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<ReturnSalesDraftEntity> ls = returnsalesdraftService.
                getReturnSalesDraftList(itemperpage, page, sort, keyword, user, warehouse);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countReturnSalesDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countReturnSalesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        return ResponseEntity.ok(returnsalesdraftService.countReturnSalesDraftList(keyword, user, warehouse));
    }
}
