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
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.ItemInvoicePurchaseService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
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
public class InvoicePurchaseController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @Autowired
    ItemInvoicePurchaseService iteminvoicepurchaseService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    PaymentOutInvoicePurchaseService paymentoutinvoicepurchaseService;
    
    @RequestMapping(value = "api/getInvoicePurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoicePurchaseList() {
        
        List<InvoicePurchaseEntity> ls = invoicepurchaseService.getInvoicePurchaseList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoicePurchase", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoicePurchase(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicepurchaseService.getInvoicePurchase(id));
    }

    @RequestMapping(value = "api/updateInvoicePurchase", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoicePurchase(HttpServletRequest httpRequest,
            @RequestBody InvoicePurchaseEntity invoicepurchase) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicepurchase.setLastmodified(Calendar.getInstance());
        invoicepurchase.setWarehouse(warehouse);
        invoicepurchase.setLastmodifiedby(user);
        
        invoicepurchaseService.updateInvoicePurchase(invoicepurchase);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/api/getInvoicePurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoicePurchaseList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<InvoicePurchaseEntity> ls = invoicepurchaseService.
                getInvoicePurchaseList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoicePurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoicePurchaseList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(invoicepurchaseService.countInvoicePurchaseList(keyword));
    }
    
    @RequestMapping(value = {"api/getUnpaidInvoicePurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"partnerid", "currencyid"})
    public ResponseEntity<?> getUnpaidInvoicePurchaseList(
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid
            ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        return ResponseEntity.ok(invoicepurchaseService.getUnpaidInvoicePurchaseList(partner, currency));
    }
    
    @RequestMapping(value = {"api/cancelInvoicePurchase"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelInvoicePurchase(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        InvoicePurchaseEntity invoicepurchase = invoicepurchaseService.getInvoicePurchase(id);
        
        List ls = paymentoutinvoicepurchaseService.getPaymentOutInvoicePurchaseEntityList(invoicepurchase);
        
        if(!ls.isEmpty()){
            return ResponseEntity.badRequest().body("You have some payment associated with this invoice");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicepurchase.setLastmodified(Calendar.getInstance());
        invoicepurchase.setWarehouse(warehouse);
        invoicepurchase.setLastmodifiedby(user);
        invoicepurchase.setStatus(false);
        
        List<ItemInvoicePurchaseEntity> iislist = 
                iteminvoicepurchaseService.getItemInvoicePurchaseList(invoicepurchase);
        
        for(ItemInvoicePurchaseEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(warehouse, item);
            
            iw.setStock(iw.getStock() - iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        invoicepurchaseService.updateInvoicePurchase(invoicepurchase);
        
        PartnerEntity partner = invoicepurchase.getPartner();
        partner.setBalance(partner.getBalance() - invoicepurchase.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
}
