/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceWarehouseInService;
import net.purnama.pureff.service.ItemInvoiceWarehouseInService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
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
public class InvoiceWarehouseInController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    InvoiceWarehouseInService invoicewarehouseinService;
    
    @Autowired
    ItemInvoiceWarehouseInService iteminvoicewarehouseinService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseInList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceWarehouseInList() {
        
        List<InvoiceWarehouseInEntity> ls = invoicewarehouseinService.getInvoiceWarehouseInList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseIn", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseIn(@RequestParam(value="id") String id){
        return ResponseEntity.ok(invoicewarehouseinService.getInvoiceWarehouseIn(id));
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseIn", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceWarehouseIn(HttpServletRequest httpRequest,
            @RequestBody InvoiceWarehouseInEntity invoicewarehousein) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicewarehousein.setLastmodified(Calendar.getInstance());
        invoicewarehousein.setWarehouse(warehouse);
        invoicewarehousein.setLastmodifiedby(user);
        
        invoicewarehouseinService.updateInvoiceWarehouseIn(invoicewarehousein);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceWarehouseInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceWarehouseInList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<InvoiceWarehouseInEntity> ls = invoicewarehouseinService.
                getInvoiceWarehouseInList(itemperpage, page, sort, keyword);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceWarehouseInList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoiceWarehouseInList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(invoicewarehouseinService.countInvoiceWarehouseInList(keyword));
    }
    
    @RequestMapping(value = {"api/cancelInvoiceWarehouseIn"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelInvoiceWarehouseIn(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        InvoiceWarehouseInEntity invoicewarehousein = invoicewarehouseinService.getInvoiceWarehouseIn(id);
        
        if(!invoicewarehousein.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
        }
        
        List<ItemInvoiceWarehouseInEntity> iislist = 
                iteminvoicewarehouseinService.getItemInvoiceWarehouseInList(invoicewarehousein);
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = invoicewarehousein.getWarehouse();
        
        invoicewarehousein.setLastmodified(Calendar.getInstance());
        invoicewarehousein.setLastmodifiedby(user);
        invoicewarehousein.setStatus(false);
        
        for(ItemInvoiceWarehouseInEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(
                    warehouse, item);
            
            iw.setStock(iw.getStock() - iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        invoicewarehousein.setStatus(false);
        invoicewarehousein.setLastmodified(Calendar.getInstance());
        invoicewarehousein.setLastmodifiedby(user);
        
        invoicewarehouseinService.updateInvoiceWarehouseIn(invoicewarehousein);
        
        return ResponseEntity.ok("");
    }
}

