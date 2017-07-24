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
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemReturnPurchaseService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
public class ReturnPurchaseController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @Autowired
    ItemReturnPurchaseService itemreturnpurchaseService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    PaymentOutReturnPurchaseService paymentoutreturnpurchaseService;
    
    @RequestMapping(value = "api/getReturnPurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getReturnPurchaseList() {
        
        List<ReturnPurchaseEntity> ls = returnpurchaseService.getReturnPurchaseList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getReturnPurchase", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnPurchase(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(returnpurchaseService.getReturnPurchase(id));
    }

    @RequestMapping(value = "api/updateReturnPurchase", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateReturnPurchase(HttpServletRequest httpRequest,
            @RequestBody ReturnPurchaseEntity returnpurchase) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        returnpurchase.setLastmodified(Calendar.getInstance());
        returnpurchase.setWarehouse(warehouse);
        returnpurchase.setLastmodifiedby(user);
        
        returnpurchaseService.updateReturnPurchase(returnpurchase);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getReturnPurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getReturnPurchaseList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<ReturnPurchaseEntity> ls = returnpurchaseService.
                getReturnPurchaseList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countReturnPurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countReturnPurchaseList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(returnpurchaseService.countReturnPurchaseList(keyword));
    }
    
    @RequestMapping(value = {"api/closeReturnPurchase"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeReturnPurchaseList(
            @RequestParam(value="id") String id){
        
        ReturnPurchaseEntity returnpurchase = returnpurchaseService.getReturnPurchase(id);
        
        returnpurchase.setPaid(returnpurchase.getTotal_after_tax());
        
        returnpurchaseService.updateReturnPurchase(returnpurchase);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getUnpaidReturnPurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"partnerid", "currencyid"})
    public ResponseEntity<?> getUnpaidReturnPurchaseList(
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid
            ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        return ResponseEntity.ok(returnpurchaseService.getUnpaidReturnPurchaseList(partner, currency));
    }
    
    @RequestMapping(value = {"api/cancelReturnPurchase"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelReturnPurchase(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        ReturnPurchaseEntity returnpurchase = returnpurchaseService.getReturnPurchase(id);
        
        List ls = paymentoutreturnpurchaseService.getPaymentOutReturnPurchaseEntityList(returnpurchase);
        
        if(!ls.isEmpty()){
            return ResponseEntity.badRequest().body("You have some payment associated with this invoice");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = returnpurchase.getWarehouse();
        
        returnpurchase.setLastmodified(Calendar.getInstance());
        returnpurchase.setLastmodifiedby(user);
        returnpurchase.setStatus(false);
        
        List<ItemReturnPurchaseEntity> iislist = 
                itemreturnpurchaseService.getItemReturnPurchaseList(returnpurchase);
        
        for(ItemReturnPurchaseEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(warehouse, item);
            
            iw.setStock(iw.getStock() + iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        returnpurchaseService.updateReturnPurchase(returnpurchase);
        
        PartnerEntity partner = returnpurchase.getPartner();
        partner.setBalance(partner.getBalance() + returnpurchase.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getReturnPurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getReturnPurchaseList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="status") boolean status){
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<ReturnPurchaseEntity> ls = returnpurchaseService.getReturnPurchaseList(start, end,
                warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
}
