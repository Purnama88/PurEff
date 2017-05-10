/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
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
public class InvoiceWarehouseOutController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehoseoutService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceWarehouseOutList() {
        
        List<InvoiceWarehouseOutEntity> ls = invoicewarehoseoutService.getInvoiceWarehouseOutList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseOut", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseOut(@RequestParam(value="id") String id){
        return ResponseEntity.ok(invoicewarehoseoutService.getInvoiceWarehouseOut(id));
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceWarehouseOut(HttpServletRequest httpRequest,
            @RequestBody InvoiceWarehouseOutEntity invoicewarehoseout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicewarehoseout.setLastmodified(Calendar.getInstance());
        invoicewarehoseout.setWarehouse(warehouse);
        invoicewarehoseout.setLastmodifiedby(user);
        
        invoicewarehoseoutService.updateInvoiceWarehouseOut(invoicewarehoseout);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceWarehouseOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceWarehouseOutList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<InvoiceWarehouseOutEntity> ls = invoicewarehoseoutService.
                getInvoiceWarehouseOutList(itemperpage, page, sort, keyword);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceWarehouseOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoiceWarehouseOutList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(invoicewarehoseoutService.countInvoiceWarehouseOutList(keyword));
    }
}

