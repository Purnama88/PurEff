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
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemReturnSalesService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentInReturnSalesService;
import net.purnama.pureff.service.ReturnSalesService;
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
public class ReturnSalesController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @Autowired
    ItemReturnSalesService itemreturnsalesService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    PaymentInReturnSalesService paymentinreturnsalesService;
    
    @RequestMapping(value = "api/getReturnSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getReturnSalesList() {
        
        List<ReturnSalesEntity> ls = returnsalesService.getReturnSalesList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getReturnSales", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnSales(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(returnsalesService.getReturnSales(id));
    }

    @RequestMapping(value = "api/updateReturnSales", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateReturnSales(HttpServletRequest httpRequest,
            @RequestBody ReturnSalesEntity returnsales) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        returnsales.setLastmodified(Calendar.getInstance());
        returnsales.setWarehouse(warehouse);
        returnsales.setLastmodifiedby(user);
        
        returnsalesService.updateReturnSales(returnsales);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getReturnSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getReturnSalesList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<ReturnSalesEntity> ls = returnsalesService.
                getReturnSalesList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countReturnSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countReturnSalesList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(returnsalesService.countReturnSalesList(keyword));
    }
    
    @RequestMapping(value = {"api/closeReturnSales"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeReturnSalesList(
            @RequestParam(value="id") String id){
        
        ReturnSalesEntity returnsales = returnsalesService.getReturnSales(id);
        
        returnsales.setPaid(returnsales.getTotal_after_tax());
        
        returnsalesService.updateReturnSales(returnsales);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getUnpaidReturnSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"partnerid", "currencyid"})
    public ResponseEntity<?> getUnpaidReturnSalesList(
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid
            ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        return ResponseEntity.ok(returnsalesService.getUnpaidReturnSalesList(partner, currency));
    }
    
    @RequestMapping(value = {"api/cancelReturnSales"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelReturnSales(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        ReturnSalesEntity returnsales = returnsalesService.getReturnSales(id);
        
        List ls = paymentinreturnsalesService.getPaymentInReturnSalesEntityList(returnsales);
        
        if(!ls.isEmpty()){
            return ResponseEntity.badRequest().body("You have some payment associated with this invoice");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = returnsales.getWarehouse();
        
        returnsales.setLastmodified(Calendar.getInstance());
        returnsales.setLastmodifiedby(user);
        returnsales.setStatus(false);
        
        List<ItemReturnSalesEntity> iislist = 
                itemreturnsalesService.getItemReturnSalesList(returnsales);
        
        for(ItemReturnSalesEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(warehouse, item);
            
            iw.setStock(iw.getStock() - iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        returnsalesService.updateReturnSales(returnsales);
        
        PartnerEntity partner = returnsales.getPartner();
        partner.setBalance(partner.getBalance() + returnsales.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getReturnSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", "partnerid", "currencyid"})
    public ResponseEntity<?> getReturnSalesList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid){
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<ReturnSalesEntity> ls = returnsalesService.getReturnSalesList(start, end, warehouse, partner, currency);
        
        return ResponseEntity.ok(ls);
    }
}
