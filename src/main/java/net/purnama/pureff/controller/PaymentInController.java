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
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentInInvoiceSalesService;
import net.purnama.pureff.service.PaymentInReturnSalesService;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentTypeInService;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.GlobalFields;
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
public class PaymentInController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @Autowired
    PaymentInInvoiceSalesService paymentininvoicesalesService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @Autowired
    PaymentInReturnSalesService paymentinreturnsalesService;
    
    @Autowired
    PaymentTypeInService paymenttypeinService;
    
    @RequestMapping(value = "api/getPaymentInList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentInList() {
        
        List<PaymentInEntity> ls = paymentinService.getPaymentInList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentIn", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentIn(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentinService.getPaymentIn(id));
    }

    @RequestMapping(value = "api/updatePaymentIn", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentIn(HttpServletRequest httpRequest,
            @RequestBody PaymentInEntity paymentin) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentin.setLastmodified(Calendar.getInstance());
        paymentin.setWarehouse(warehouse);
        paymentin.setLastmodifiedby(user);
        
        paymentinService.updatePaymentIn(paymentin);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentInList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PaymentInEntity> ls = paymentinService.
                getPaymentInList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentInList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPaymentInList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(paymentinService.countPaymentInList(keyword));
    }
    
    @RequestMapping(value = "api/cancelPaymentIn", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelPaymentIn(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        
        double totalbalance = 0;
        
        PaymentInEntity paymentin = paymentinService.getPaymentIn(id);
        
        List<PaymentInInvoiceSalesEntity> piislist = 
                paymentininvoicesalesService.getPaymentInInvoiceSalesEntityList(paymentin);
        
        for(PaymentInInvoiceSalesEntity piis : piislist){
            
            InvoiceSalesEntity invoicesales = piis.getInvoicesales();
            
            invoicesales.setPaid(invoicesales.getPaid() - piis.getAmount());
            
            invoicesalesService.updateInvoiceSales(invoicesales);
            
            totalbalance += piis.getAmount() * invoicesales.getRate();
            
            piis.setInvoicesales(null);
            
            paymentininvoicesalesService.updatePaymentinInvoiceSales(piis);
        }
        
        List<PaymentInReturnSalesEntity> pirslist = 
                paymentinreturnsalesService.getPaymentInReturnSalesEntityList(paymentin);
        
        for(PaymentInReturnSalesEntity pirs : pirslist){
            
            ReturnSalesEntity returnsales = pirs.getReturnsales();
            
            returnsales.setPaid(returnsales.getPaid() - pirs.getAmount());
            
            returnsalesService.updateReturnSales(returnsales);
            
            totalbalance -= pirs.getAmount() * returnsales.getRate();
            
            pirs.setReturnsales(null);
            
            paymentinreturnsalesService.updatePaymentInReturnSales(pirs);
        }
        
        for(PaymentTypeInEntity ptde : paymenttypeinService.
                getPaymentTypeInList(paymentin)){
            ptde.setStatus(false);
            ptde.setValid(false);
            
            paymenttypeinService.updatePaymentTypeIn(ptde);
        }
        
        paymentin.setStatus(false);
        paymentin.setLastmodified(Calendar.getInstance());
        paymentin.setLastmodifiedby(user);
        
        paymentinService.updatePaymentIn(paymentin);
        
        PartnerEntity partner = paymentin.getPartner();
        if(partner.getPartnertype().getParent() == GlobalFields.CUSTOMER){
            double remaining = paymentin.getAmount() - totalbalance;
            partner.setBalance(partner.getBalance() + totalbalance + remaining);
        }
        else{
            partner.setBalance(partner.getBalance() - (paymentin.getAmount() * paymentin.getRate()));
        }
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getPaymentInList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getPaymentInList(
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
        
        List<PaymentInEntity> ls = paymentinService.
                getPaymentInList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
}
