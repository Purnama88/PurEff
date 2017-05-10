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
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceSalesDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.InvoiceSalesDraftService;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.ItemInvoiceSalesDraftService;
import net.purnama.pureff.service.ItemInvoiceSalesService;
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
public class InvoiceSalesDraftController {
    
    @Autowired
    InvoiceSalesDraftService invoicesalesdraftService;
    
    @Autowired
    ItemInvoiceSalesDraftService iteminvoicesalesdraftService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @Autowired
    ItemInvoiceSalesService iteminvoicesalesService;
    
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
    
    @RequestMapping(value = "api/getInvoiceSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceSalesDraftList() {
        
        List<InvoiceSalesDraftEntity> ls = invoicesalesdraftService.getInvoiceSalesDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceSalesDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicesalesdraftService.getInvoiceSalesDraft(id));
    }
    
    @RequestMapping(value = "api/addInvoiceSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addInvoiceSalesDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(8);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu invoice sales");
        }
        
        CurrencyEntity currency = currencyService.getDefaultCurrency();
        RateEntity rate = rateService.getLastRate(currency);
        Calendar date = Calendar.getInstance();
        
        InvoiceSalesDraftEntity invoicesalesdraft = new InvoiceSalesDraftEntity();
        invoicesalesdraft.setId(IdGenerator.generateId());
        invoicesalesdraft.setDate(date);
        invoicesalesdraft.setDuedate(date);
        invoicesalesdraft.setWarehouse(warehouse);
        invoicesalesdraft.setNumbering(numbering);
        invoicesalesdraft.setNote("");
        invoicesalesdraft.setSubtotal(0);
        invoicesalesdraft.setDiscount(0);
        invoicesalesdraft.setRounding(0);
        invoicesalesdraft.setFreight(0);
        invoicesalesdraft.setTax(0);
        invoicesalesdraft.setLastmodified(date);
        invoicesalesdraft.setLastmodifiedby(user);
        invoicesalesdraft.setCurrency(currency);
        if(rate == null){
            invoicesalesdraft.setRate(1);
        }
        else{
            invoicesalesdraft.setRate(rate.getValue());
        }
        
        invoicesalesdraftService.addInvoiceSalesDraft(invoicesalesdraft);
        
        return ResponseEntity.ok(invoicesalesdraft.getId());
    }

    @RequestMapping(value = "api/closeInvoiceSalesDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoiceSalesDraft(@RequestParam(value="id") String id) {
        InvoiceSalesDraftEntity invoicesalesdraft = invoicesalesdraftService.getInvoiceSalesDraft(id);
        
        NumberingEntity numbering = invoicesalesdraft.getNumbering();
        
        InvoiceSalesEntity invoicesales = new InvoiceSalesEntity();
        invoicesales.setId(IdGenerator.generateId());
        invoicesales.setNumber(numbering.getCurrentId());
        invoicesales.setDate(invoicesalesdraft.getDate());
        invoicesales.setPrinted(0);
        invoicesales.setWarehouse(invoicesalesdraft.getWarehouse());
        invoicesales.setDraftid(invoicesalesdraft.getId());
        invoicesales.setDuedate(invoicesalesdraft.getDuedate());
        invoicesales.setSubtotal(invoicesalesdraft.getSubtotal());
        invoicesales.setDiscount(invoicesalesdraft.getDiscount());
        invoicesales.setRounding(invoicesalesdraft.getRounding());
        invoicesales.setFreight(invoicesalesdraft.getFreight());
        invoicesales.setTax(invoicesalesdraft.getTax());
        invoicesales.setRate(invoicesalesdraft.getRate());
        invoicesales.setPartner(invoicesalesdraft.getPartner());
        invoicesales.setCurrency(invoicesalesdraft.getCurrency());
        invoicesales.setCurrency_code(invoicesalesdraft.getCurrency().getCode());
        invoicesales.setCurrency_name(invoicesalesdraft.getCurrency().getName());
        invoicesales.setPartner_name(invoicesalesdraft.getPartner().getName());
        invoicesales.setPartner_code(invoicesalesdraft.getPartner().getCode());
        invoicesales.setPartner_address(invoicesalesdraft.getPartner().getAddress());
        invoicesales.setNote(invoicesalesdraft.getNote());
        invoicesales.setLastmodified(Calendar.getInstance());
        invoicesales.setLastmodifiedby(invoicesalesdraft.getLastmodifiedby());
        invoicesales.setStatus(true);
        invoicesales.setUser_code(invoicesales.getLastmodifiedby().getCode());
        invoicesales.setWarehouse_code(invoicesales.getWarehouse().getCode());
        invoicesales.setPaid(0);
        invoicesalesService.addInvoiceSales(invoicesales);
        
        List<ItemInvoiceSalesDraftEntity> iisdelist = iteminvoicesalesdraftService.
                getItemInvoiceSalesDraftList(invoicesalesdraft);
        
        for(ItemInvoiceSalesDraftEntity iisde : iisdelist){
            ItemInvoiceSalesEntity iise = new ItemInvoiceSalesEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setPrice(iisde.getPrice());
            iise.setDiscount(iisde.getDiscount());
            iise.setCost(iisde.getItem().getCost());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoicesales(invoicesales);
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
            iteminvoicesalesService.addItemInvoiceSales(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(invoicesales.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() - iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        PartnerEntity partner = invoicesales.getPartner();
        partner.setBalance(partner.getBalance() + invoicesales.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemInvoiceSalesDraftEntity idd : iteminvoicesalesdraftService.getItemInvoiceSalesDraftList(invoicesalesdraft)){
            iteminvoicesalesdraftService.deleteItemInvoiceSalesDraft(idd.getId());
        }
        
        invoicesalesdraftService.deleteInvoiceSalesDraft(id);
        
        return ResponseEntity.ok(invoicesales.getId());
    }
    
    @RequestMapping(value = "api/updateInvoiceSalesDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceSalesDraft(HttpServletRequest httpRequest, 
            @RequestBody InvoiceSalesDraftEntity invoicesalesdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicesalesdraft.setLastmodified(Calendar.getInstance());
        invoicesalesdraft.setWarehouse(warehouse);
        invoicesalesdraft.setLastmodifiedby(user);
        
        invoicesalesdraftService.updateInvoiceSalesDraft(invoicesalesdraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteInvoiceSalesDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteInvoiceSalesDraft(@RequestParam(value="id") String id) {
        InvoiceSalesDraftEntity invoicesalesdraft = invoicesalesdraftService.getInvoiceSalesDraft(id);
        
        for(ItemInvoiceSalesDraftEntity idd : iteminvoicesalesdraftService.getItemInvoiceSalesDraftList(invoicesalesdraft)){
            iteminvoicesalesdraftService.deleteItemInvoiceSalesDraft(idd.getId());
        }
        
        invoicesalesdraftService.deleteInvoiceSalesDraft(id);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceSalesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<InvoiceSalesDraftEntity> ls = invoicesalesdraftService.
                getInvoiceSalesDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceSalesDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoiceSalesDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return ResponseEntity.ok(invoicesalesdraftService.countInvoiceSalesDraftList(keyword, user));
    }
}
