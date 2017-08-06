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
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceWarehouseInDraftService;
import net.purnama.pureff.service.InvoiceWarehouseOutDraftService;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInDraftService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutDraftService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
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
public class InvoiceWarehouseOutDraftController {
    
    @Autowired
    InvoiceWarehouseInDraftService invoicewarehouseindraftService;
    
    @Autowired
    ItemInvoiceWarehouseInDraftService iteminvoicewarehouseindraftService;
    
    @Autowired
    InvoiceWarehouseOutDraftService invoicewarehouseoutdraftService;
    
    @Autowired
    ItemInvoiceWarehouseOutDraftService iteminvoicewarehouseoutdraftService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @Autowired
    ItemInvoiceWarehouseOutService iteminvoicewarehouseoutService;
    
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
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceWarehouseOutDraftList() {
        
        List<InvoiceWarehouseOutDraftEntity> ls = invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseOutDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraft(id));
    }

    @RequestMapping(value = "api/addInvoiceWarehouseOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addInvoiceWarehouseOutDraft(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(6);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu invoice warehouse out");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        Calendar date = Calendar.getInstance();
        
        InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft = new InvoiceWarehouseOutDraftEntity();
        invoicewarehouseoutdraft.setId(IdGenerator.generateId());
        invoicewarehouseoutdraft.setDate(date);
        invoicewarehouseoutdraft.setLastmodified(date);
        invoicewarehouseoutdraft.setLastmodifiedby(user);
        invoicewarehouseoutdraft.setNote("");
        invoicewarehouseoutdraft.setNumbering(numbering);
        invoicewarehouseoutdraft.setShipping_number("");
        invoicewarehouseoutdraft.setStatus(true);
        invoicewarehouseoutdraft.setWarehouse(warehouse);
        
        invoicewarehouseoutdraftService.addInvoiceWarehouseOutDraft(invoicewarehouseoutdraft);
        
        return ResponseEntity.ok(invoicewarehouseoutdraft.getId());
    }

    @RequestMapping(value = "api/closeInvoiceWarehouseOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoiceWarehouseOutDraft(@RequestParam(value="id") String id) {
        InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft = invoicewarehouseoutdraftService.
                getInvoiceWarehouseOutDraft(id);
        
        NumberingEntity numbering = invoicewarehouseoutdraft.getNumbering();
        
        List<ItemInvoiceWarehouseOutDraftEntity> iisdelist = iteminvoicewarehouseoutdraftService.
                getItemInvoiceWarehouseOutDraftList(invoicewarehouseoutdraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(invoicewarehouseoutdraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(invoicewarehouseoutdraft.getWarehouse().getId().equals(invoicewarehouseoutdraft.getDestination().getId())){
            return ResponseEntity.badRequest().body("Origin & Destination is the same warehouse");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        InvoiceWarehouseOutEntity invoicewarehouseout = new InvoiceWarehouseOutEntity();
        invoicewarehouseout.setId(IdGenerator.generateId());
        invoicewarehouseout.setNumber(numbering.getCurrentId());
        invoicewarehouseout.setDate(invoicewarehouseoutdraft.getDate());
        invoicewarehouseout.setPrinted(0);
        invoicewarehouseout.setWarehouse(invoicewarehouseoutdraft.getWarehouse());
        invoicewarehouseout.setDraftid(invoicewarehouseoutdraft.getId());
        invoicewarehouseout.setDestination(invoicewarehouseoutdraft.getDestination());
        invoicewarehouseout.setDestination_code(invoicewarehouseout.getDestination().getCode());
        invoicewarehouseout.setNote(invoicewarehouseoutdraft.getNote());
        invoicewarehouseout.setLastmodified(Calendar.getInstance());
        invoicewarehouseout.setLastmodifiedby(invoicewarehouseoutdraft.getLastmodifiedby());
        invoicewarehouseout.setStatus(true);
        invoicewarehouseout.setUser_code(invoicewarehouseoutdraft.getLastmodifiedby().getCode());
        invoicewarehouseout.setWarehouse_code(invoicewarehouseoutdraft.getWarehouse().getCode());
        invoicewarehouseout.setShipping_number(invoicewarehouseoutdraft.getShipping_number());
        
        invoicewarehouseoutService.addInvoiceWarehouseOut(invoicewarehouseout);
        
        MenuEntity menu = menuService.getMenu(5);
        NumberingEntity numbering2 = numberingService.getDefaultNumbering(menu, invoicewarehouseoutdraft.getWarehouse());
        
        if(numbering2 == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu invoice warehouse in");
        }
        else if(numbering2.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering2.setStatus(false);
            numbering2.setLastmodified(Calendar.getInstance());
            numbering2.setLastmodifiedby(invoicewarehouseoutdraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering2);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        Calendar date = Calendar.getInstance();
        
        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = new InvoiceWarehouseInDraftEntity();
        invoicewarehouseindraft.setId(IdGenerator.generateId());
        invoicewarehouseindraft.setDate(date);
        invoicewarehouseindraft.setLastmodified(date);
        invoicewarehouseindraft.setLastmodifiedby(invoicewarehouseoutdraft.getLastmodifiedby());
        invoicewarehouseindraft.setNote("");
        invoicewarehouseindraft.setNumbering(numbering);
        invoicewarehouseindraft.setShipping_number(invoicewarehouseoutdraft.getShipping_number());
        invoicewarehouseindraft.setStatus(true);
        invoicewarehouseindraft.setWarehouse(invoicewarehouseoutdraft.getDestination());
        invoicewarehouseindraft.setOrigin(invoicewarehouseoutdraft.getWarehouse());
        
        invoicewarehouseindraftService.addInvoiceWarehouseInDraft(invoicewarehouseindraft);
        
        for(ItemInvoiceWarehouseOutDraftEntity iisde : iisdelist){
            ItemInvoiceWarehouseOutEntity iise = new ItemInvoiceWarehouseOutEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setItem(iisde.getItem());
            iise.setUom(iisde.getUom());
            iise.setInvoicewarehouseout(invoicewarehouseout);
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
            iteminvoicewarehouseoutService.addItemInvoiceWarehouseOut(iise);
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(invoicewarehouseout.getWarehouse(), iise.getItem());
            iw.setStock(iw.getStock() - iise.getBasequantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemInvoiceWarehouseOutDraftEntity idd : iteminvoicewarehouseoutdraftService.
                getItemInvoiceWarehouseOutDraftList(invoicewarehouseoutdraft)){
            iteminvoicewarehouseoutdraftService.deleteItemInvoiceWarehouseOutDraft(idd.getId());
        }
        
        invoicewarehouseoutdraftService.deleteInvoiceWarehouseOutDraft(id);
        
        return ResponseEntity.ok(invoicewarehouseout.getId());
    }
    
    @RequestMapping(value = "api/updateInvoiceWarehouseOutDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceWarehouseOutDraft(HttpServletRequest httpRequest, 
            @RequestBody InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicewarehouseoutdraft.setLastmodified(Calendar.getInstance());
        invoicewarehouseoutdraft.setWarehouse(warehouse);
        invoicewarehouseoutdraft.setLastmodifiedby(user);
        
        invoicewarehouseoutdraftService.updateInvoiceWarehouseOutDraft(invoicewarehouseoutdraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deleteInvoiceWarehouseOutDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteInvoiceWarehouseOutDraft(@RequestParam(value="id") String id) {
        InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft = 
                invoicewarehouseoutdraftService.getInvoiceWarehouseOutDraft(id);
        
        for(ItemInvoiceWarehouseOutDraftEntity idd : iteminvoicewarehouseoutdraftService.
                getItemInvoiceWarehouseOutDraftList(invoicewarehouseoutdraft)){
            iteminvoicewarehouseoutdraftService.deleteItemInvoiceWarehouseOutDraft(idd.getId());
        }
        
        invoicewarehouseoutdraftService.deleteInvoiceWarehouseOutDraft(id);		
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceWarehouseOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceWarehouseOutDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<InvoiceWarehouseOutDraftEntity> ls = invoicewarehouseoutdraftService.
                getInvoiceWarehouseOutDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceWarehouseOutDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public int countInvoiceWarehouseOutDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return invoicewarehouseoutdraftService.countInvoiceWarehouseOutDraftList(keyword, user);
    }
}
