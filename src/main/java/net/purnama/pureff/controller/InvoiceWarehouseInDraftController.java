/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceWarehouseInDraftService;
import net.purnama.pureff.service.InvoiceWarehouseInService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInDraftService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PartnerService;
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
public class InvoiceWarehouseInDraftController {
    
    @Autowired
    InvoiceWarehouseInDraftService invoicewarehouseindraftService;
    
    @Autowired
    ItemInvoiceWarehouseInDraftService iteminvoicewarehouseindraftService;
    
    @Autowired
    InvoiceWarehouseInService invoicewarehouseinService;
    
    @Autowired
    ItemInvoiceWarehouseInService iteminvoicewarehouseinService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    NumberingService numberingService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceWarehouseInDraftList() {
        
        List<InvoiceWarehouseInDraftEntity> ls = invoicewarehouseindraftService.getInvoiceWarehouseInDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseInDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicewarehouseindraftService.getInvoiceWarehouseInDraft(id));
    }

    @RequestMapping(value = "api/addInvoiceWarehouseInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addInvoiceWarehouseInDraft(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(5);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu invoice warehouse in");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        Calendar date = Calendar.getInstance();
        
        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = new InvoiceWarehouseInDraftEntity();
        invoicewarehouseindraft.setId(IdGenerator.generateId());
        invoicewarehouseindraft.setDate(date);
        invoicewarehouseindraft.setLastmodified(date);
        invoicewarehouseindraft.setLastmodifiedby(user);
        invoicewarehouseindraft.setNote("");
        invoicewarehouseindraft.setNumbering(numbering);
        invoicewarehouseindraft.setShipping_number("");
        invoicewarehouseindraft.setStatus(true);
        invoicewarehouseindraft.setWarehouse(warehouse);
        
        invoicewarehouseindraftService.addInvoiceWarehouseInDraft(invoicewarehouseindraft);
        
        return ResponseEntity.ok(invoicewarehouseindraft.getId());
    }

    @RequestMapping(value = "api/closeInvoiceWarehouseInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoiceWarehouseInDraft(@RequestParam(value="id") String id) {
        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = invoicewarehouseindraftService.
                getInvoiceWarehouseInDraft(id);
        
        NumberingEntity numbering = invoicewarehouseindraft.getNumbering();
        
        List<ItemInvoiceWarehouseInDraftEntity> iisdelist = iteminvoicewarehouseindraftService.
                getItemInvoiceWarehouseInDraftList(invoicewarehouseindraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(invoicewarehouseindraft.getWarehouse().getId().equals(invoicewarehouseindraft.getOrigin().getId())){
            return ResponseEntity.badRequest().body("Origin & Destination is the same warehouse");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        InvoiceWarehouseInEntity invoicewarehousein = new InvoiceWarehouseInEntity();
        invoicewarehousein.setId(IdGenerator.generateId());
        invoicewarehousein.setNumber(numbering.getCurrentId());
        invoicewarehousein.setDate(invoicewarehouseindraft.getDate());
        invoicewarehousein.setPrinted(0);
        invoicewarehousein.setWarehouse(invoicewarehouseindraft.getWarehouse());
        invoicewarehousein.setDraftid(invoicewarehouseindraft.getId());
        invoicewarehousein.setOrigin(invoicewarehouseindraft.getOrigin());
        invoicewarehousein.setOrigin_code(invoicewarehousein.getOrigin().getCode());
        invoicewarehousein.setNote(invoicewarehouseindraft.getNote());
        invoicewarehousein.setLastmodified(Calendar.getInstance());
        invoicewarehousein.setLastmodifiedby(invoicewarehouseindraft.getLastmodifiedby());
        invoicewarehousein.setStatus(true);
        invoicewarehousein.setUser_code(invoicewarehouseindraft.getLastmodifiedby().getCode());
        invoicewarehousein.setWarehouse_code(invoicewarehouseindraft.getWarehouse().getCode());
        invoicewarehousein.setShipping_number(invoicewarehouseindraft.getShipping_number());
        
        invoicewarehouseinService.addInvoiceWarehouseIn(invoicewarehousein);
        
        for(ItemInvoiceWarehouseInDraftEntity iisde : iisdelist){
            ItemInvoiceWarehouseInEntity iise = new ItemInvoiceWarehouseInEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoicewarehousein(invoicewarehousein);
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
            iteminvoicewarehouseinService.addItemInvoiceWarehouseIn(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(invoicewarehousein.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() + iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemInvoiceWarehouseInDraftEntity idd : iteminvoicewarehouseindraftService.
                getItemInvoiceWarehouseInDraftList(invoicewarehouseindraft)){
            iteminvoicewarehouseindraftService.deleteItemInvoiceWarehouseInDraft(idd.getId());
        }
        
        invoicewarehouseindraftService.deleteInvoiceWarehouseInDraft(id);
        
        return ResponseEntity.ok(invoicewarehousein.getId());
    }
    
    @RequestMapping(value = "api/updateInvoiceWarehouseInDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceWarehouseInDraft(HttpServletRequest httpRequest, 
            @RequestBody InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicewarehouseindraft.setLastmodified(Calendar.getInstance());
        invoicewarehouseindraft.setWarehouse(warehouse);
        invoicewarehouseindraft.setLastmodifiedby(user);
        
        invoicewarehouseindraftService.updateInvoiceWarehouseInDraft(invoicewarehouseindraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteInvoiceWarehouseInDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteInvoiceWarehouseInDraft(@RequestParam(value="id") String id) {
        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = 
                invoicewarehouseindraftService.getInvoiceWarehouseInDraft(id);
        
        for(ItemInvoiceWarehouseInDraftEntity idd : iteminvoicewarehouseindraftService.
                getItemInvoiceWarehouseInDraftList(invoicewarehouseindraft)){
            iteminvoicewarehouseindraftService.deleteItemInvoiceWarehouseInDraft(idd.getId());
        }
        
        invoicewarehouseindraftService.deleteInvoiceWarehouseInDraft(id);		
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceWarehouseInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceWarehouseInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<InvoiceWarehouseInDraftEntity> ls = invoicewarehouseindraftService.
                getInvoiceWarehouseInDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceWarehouseInDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public int countInvoiceWarehouseInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return invoicewarehouseindraftService.countInvoiceWarehouseInDraftList(keyword, user);
    }
}
