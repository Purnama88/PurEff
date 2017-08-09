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
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.ItemInvoiceSalesService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentInInvoiceSalesService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.CalendarUtil;
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
public class InvoiceSalesController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @Autowired
    ItemInvoiceSalesService iteminvoicesalesService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    PaymentInInvoiceSalesService paymentininvoicesalesService;
    
    @RequestMapping(value = "api/getInvoiceSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceSalesList() {
        
        List<InvoiceSalesEntity> ls = invoicesalesService.getInvoiceSalesList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceSales", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceSales(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(invoicesalesService.getInvoiceSales(id));
    }

    @RequestMapping(value = "api/updateInvoiceSales", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceSales(HttpServletRequest httpRequest,
            @RequestBody InvoiceSalesEntity invoicesales) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicesales.setLastmodified(Calendar.getInstance());
        invoicesales.setWarehouse(warehouse);
        invoicesales.setLastmodifiedby(user);
        
        invoicesalesService.updateInvoiceSales(invoicesales);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceSalesList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<InvoiceSalesEntity> ls = invoicesalesService.
                getInvoiceSalesList(itemperpage, page, sort, keyword);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoiceSalesList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(invoicesalesService.countInvoiceSalesList(keyword));
    }
    
    @RequestMapping(value = {"api/closeInvoiceSales"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoiceSalesList(
            @RequestParam(value="id") String id){
        
        InvoiceSalesEntity invoicesales = invoicesalesService.getInvoiceSales(id);
        
        invoicesales.setPaid(invoicesales.getTotal_after_tax());
        
        invoicesalesService.updateInvoiceSales(invoicesales);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getUnpaidInvoiceSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"partnerid", "currencyid"})
    public ResponseEntity<?> getUnpaidInvoiceSalesList(
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        return ResponseEntity.ok(invoicesalesService.getUnpaidInvoiceSalesList(partner, currency));
    }
    
    @RequestMapping(value = {"api/cancelInvoiceSales"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelInvoiceSales(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        InvoiceSalesEntity invoicesales = invoicesalesService.getInvoiceSales(id);
        
        List ls = paymentininvoicesalesService.getPaymentInInvoiceSalesEntityList(invoicesales);
        
        if(!ls.isEmpty()){
            return ResponseEntity.badRequest().body("You have some payment associated with this invoice");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = invoicesales.getWarehouse();
        
        invoicesales.setLastmodified(Calendar.getInstance());
        invoicesales.setLastmodifiedby(user);
        invoicesales.setStatus(false);
        
        List<ItemInvoiceSalesEntity> iislist = 
                iteminvoicesalesService.getItemInvoiceSalesList(invoicesales);
        
        for(ItemInvoiceSalesEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(warehouse, item);
            
            iw.setStock(iw.getStock() + iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        invoicesalesService.updateInvoiceSales(invoicesales);
        
        PartnerEntity partner = invoicesales.getPartner();
        partner.setBalance(partner.getBalance() - invoicesales.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getInvoiceSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getInvoiceSalesList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="status") boolean status){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<InvoiceSalesEntity> ls = invoicesalesService.
                getInvoiceSalesList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
}
