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
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.RateEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.PaymentInInvoiceSalesDraftService;
import net.purnama.pureff.service.PaymentInInvoiceSalesService;
import net.purnama.pureff.service.PaymentInReturnSalesDraftService;
import net.purnama.pureff.service.PaymentInReturnSalesService;
import net.purnama.pureff.service.PaymentInService;
import net.purnama.pureff.service.PaymentTypeInDraftService;
import net.purnama.pureff.service.PaymentTypeInService;
import net.purnama.pureff.service.RateService;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.util.GlobalFields;
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
public class PaymentInDraftController {
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @Autowired
    PaymentInInvoiceSalesDraftService paymentininvoicesalesdraftService;
    
    @Autowired
    PaymentInInvoiceSalesService paymentininvoicesalesService;
    
    @Autowired
    PaymentInReturnSalesDraftService paymentinreturnsalesdraftService;
    
    @Autowired
    PaymentInReturnSalesService paymentinreturnsalesService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    RateService rateService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    NumberingService numberingService;
    
    @Autowired
    PaymentTypeInDraftService paymenttypeindraftService;
    
    @Autowired
    PaymentTypeInService paymenttypeinService;
    
    @Autowired
    PartnerService partnerService;
    
    @RequestMapping(value = "api/getPaymentInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentInDraftList() {
        
        List<PaymentInDraftEntity> ls = paymentindraftService.getPaymentInDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentInDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentindraftService.getPaymentInDraft(id));
    }

    @RequestMapping(value = "api/addPaymentInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addPaymentInDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        MenuEntity menu = menuService.getMenu(15);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu incoming payment");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        CurrencyEntity currency = currencyService.getDefaultCurrency();
        RateEntity rate = rateService.getLastRate(currency);
        Calendar date = Calendar.getInstance();
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(IdGenerator.generateId());
        paymentindraft.setDate(date);
        paymentindraft.setDuedate(date);
        paymentindraft.setAmount(0);
        paymentindraft.setWarehouse(warehouse);
        paymentindraft.setNote("");
        paymentindraft.setLastmodified(date);
        paymentindraft.setLastmodifiedby(user);
        paymentindraft.setNumbering(numbering);
        paymentindraft.setCurrency(currencyService.getDefaultCurrency());
        if(rate == null){
            paymentindraft.setRate(1);
        }
        else{
            paymentindraft.setRate(rate.getValue());
        }
        
        paymentindraftService.addPaymentInDraft(paymentindraft);
        
        return ResponseEntity.ok(paymentindraft.getId());
    }

    @RequestMapping(value = "api/updatePaymentInDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentInDraft(HttpServletRequest httpRequest,
            @RequestBody PaymentInDraftEntity paymentindraft) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        paymentindraft.setLastmodified(Calendar.getInstance());
        paymentindraft.setWarehouse(warehouse);
        paymentindraft.setLastmodifiedby(user);
        
        paymentindraftService.updatePaymentInDraft(paymentindraft);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentInDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        List<PaymentInDraftEntity> ls = paymentindraftService.
                getPaymentInDraftList(itemperpage, page, sort, keyword, user, warehouse);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentInDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPaymentInDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        return ResponseEntity.ok(paymentindraftService.countPaymentInDraftList(keyword, user, warehouse));
    }
    
    @RequestMapping(value = "api/deletePaymentInDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deletePaymentInDraft(@RequestParam(value="id") String id){
        
        PaymentInDraftEntity paymentindraft = paymentindraftService.getPaymentInDraft(id);
        
        List<PaymentInInvoiceSalesDraftEntity> islist = 
                paymentininvoicesalesdraftService.getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
        
        List<PaymentInReturnSalesDraftEntity> rslist = 
                paymentinreturnsalesdraftService.getPaymentInReturnSalesDraftEntityList(paymentindraft);
        
        List<PaymentTypeInDraftEntity> ptdlist = 
                paymenttypeindraftService.getPaymentTypeInDraftList(paymentindraft);
        
        for(PaymentInInvoiceSalesDraftEntity is : islist){
            paymentininvoicesalesdraftService.deletePaymentinInvoiceSalesDraft(is.getId());
        }
        
        for(PaymentInReturnSalesDraftEntity rs : rslist){
            paymentinreturnsalesdraftService.deletePaymentInReturnSalesDraft(rs.getId());
        }
        
        for(PaymentTypeInDraftEntity ptd : ptdlist){
            paymenttypeindraftService.deletePaymentTypeInDraft(ptd.getId());
        }
        
        paymentindraftService.deletePaymentInDraft(paymentindraft.getId());
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/closePaymentInDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closePaymentInDraft(@RequestParam(value="id") String id) {
        PaymentInDraftEntity paymentindraft = paymentindraftService.getPaymentInDraft(id);
        
        NumberingEntity numbering = paymentindraft.getNumbering();
        
        List<PaymentTypeInDraftEntity> iisdelist = paymenttypeindraftService.
                getPaymentTypeInDraftList(paymentindraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(paymentindraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(paymentindraft.getCurrency() == null){
            return ResponseEntity.badRequest().body("Currency is not valid");
        }
        else if(paymentindraft.getPartner() == null){
            return ResponseEntity.badRequest().body("Partner is not valid");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        else if(paymentindraft.getDuedate().getTime().getDate() < paymentindraft.getDate().getTime().getDate()){
            return ResponseEntity.badRequest().body("Due date is set before invoice date");
        }
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(IdGenerator.generateId());
        paymentin.setNumber(numbering.getCurrentId());
        paymentin.setDate(paymentindraft.getDate());
        paymentin.setPrinted(0);
        paymentin.setWarehouse(paymentindraft.getWarehouse());
        paymentin.setDraftid(paymentindraft.getId());
        paymentin.setDuedate(paymentindraft.getDuedate());
        paymentin.setAmount(paymentindraft.getAmount());
        paymentin.setCurrency(paymentindraft.getCurrency());
        paymentin.setRate(paymentindraft.getRate());
        paymentin.setPartner(paymentindraft.getPartner());
        paymentin.setNote(paymentindraft.getNote());
        paymentin.setLastmodified(Calendar.getInstance());
        paymentin.setLastmodifiedby(paymentindraft.getLastmodifiedby());
        paymentin.setStatus(true);
        paymentin.setCurrency_code(paymentindraft.getCurrency().getCode());
        paymentin.setCurrency_name(paymentindraft.getCurrency().getName());
        paymentin.setPartner_name(paymentindraft.getPartner().getName());
        paymentin.setPartner_code(paymentindraft.getPartner().getCode());
        paymentin.setPartner_address(paymentindraft.getPartner().getAddress());
        paymentin.setUser_code(paymentin.getLastmodifiedby().getCode());
        paymentin.setWarehouse_code(paymentin.getWarehouse().getCode());
        paymentinService.addPaymentIn(paymentin);
        
        double totalbalance = 0;
        
        for(PaymentInInvoiceSalesDraftEntity piisd : 
                paymentininvoicesalesdraftService.getPaymentInInvoiceSalesDraftEntityList(paymentindraft)){
            PaymentInInvoiceSalesEntity prs = new PaymentInInvoiceSalesEntity();
            prs.setId(IdGenerator.generateId());
            prs.setAmount(piisd.getAmount());
            prs.setPaymentin(paymentin);
            prs.setInvoicesales(piisd.getInvoicesales());
            
            prs.setInvoice_id(piisd.getInvoicesales().getNumber());
            prs.setInvoice_warehouse(piisd.getInvoicesales().getWarehouse_code());
            prs.setInvoice_currency(piisd.getInvoicesales().getCurrency_code());
            prs.setInvoice_date(piisd.getInvoicesales().getFormattedDate());
            prs.setInvoice_duedate(piisd.getInvoicesales().getFormattedDueDate());
            prs.setInvoice_total(piisd.getInvoicesales().getFormattedTotal_after_tax());
            
            paymentininvoicesalesService.addPaymentinInvoiceSales(prs);
            
            InvoiceSalesEntity rs = piisd.getInvoicesales();
            rs.setPaid(rs.getPaid() + piisd.getAmount());
            invoicesalesService.updateInvoiceSales(rs);
            
            totalbalance -= piisd.getAmount() * rs.getRate();
        }
        
        for(PaymentInReturnSalesDraftEntity pirsd : 
                paymentinreturnsalesdraftService.getPaymentInReturnSalesDraftEntityList(paymentindraft)){
            PaymentInReturnSalesEntity prs = new PaymentInReturnSalesEntity();
            prs.setId(IdGenerator.generateId());
            prs.setAmount(pirsd.getAmount());
            prs.setPaymentin(paymentin);
            prs.setReturnsales(pirsd.getReturnsales());
            
            prs.setInvoice_id(pirsd.getReturnsales().getNumber());
            prs.setInvoice_warehouse(pirsd.getReturnsales().getWarehouse_code());
            prs.setInvoice_currency(pirsd.getReturnsales().getCurrency_code());
            prs.setInvoice_date(pirsd.getReturnsales().getFormattedDate());
            prs.setInvoice_duedate(pirsd.getReturnsales().getFormattedDueDate());
            prs.setInvoice_total(pirsd.getReturnsales().getFormattedTotal_after_tax());
            
            paymentinreturnsalesService.addPaymentInReturnSales(prs);
            
            ReturnSalesEntity rs = pirsd.getReturnsales();
            rs.setPaid(rs.getPaid() + pirsd.getAmount());
            returnsalesService.updateReturnSales(rs);
            
            totalbalance += pirsd.getAmount() * rs.getRate();
        }
        
        for(PaymentTypeInDraftEntity ptide : iisdelist){
            
            PaymentTypeInEntity ptie = new PaymentTypeInEntity();
            ptie.setAmount(ptide.getAmount());
            ptie.setBank(ptide.getBank());
            ptie.setDate(ptide.getDate());
            ptie.setDuedate(ptide.getDuedate());
            ptie.setExpirydate(ptide.getExpirydate());
            ptie.setId(IdGenerator.generateId());
            ptie.setLastmodified(Calendar.getInstance());
            ptie.setLastmodifiedby(ptide.getLastmodifiedby());
            ptie.setNote(ptide.getNote());
            ptie.setNumber(ptide.getNumber());
            ptie.setPaymentin(paymentin);
            ptie.setStatus(false);
            ptie.setValid(true);
            ptie.setType(ptide.getType());
            
            paymenttypeinService.addPaymentTypeIn(ptie);
        }
        
        PartnerEntity partner = paymentin.getPartner();
        
        if(partner.getPartnertype().getParent() == GlobalFields.CUSTOMER){
            double remaining = paymentin.getAmount() + totalbalance;
            partner.setBalance(partner.getBalance() + totalbalance - remaining);
        }
        else{
            partner.setBalance(partner.getBalance() + (paymentin.getAmount() * paymentin.getRate()));
        }
        
        partnerService.updatePartner(partner);
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        List<PaymentInInvoiceSalesDraftEntity> islist = 
                paymentininvoicesalesdraftService.getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
        
        List<PaymentInReturnSalesDraftEntity> rslist = 
                paymentinreturnsalesdraftService.getPaymentInReturnSalesDraftEntityList(paymentindraft);
        
        List<PaymentTypeInDraftEntity> ptdlist = 
                paymenttypeindraftService.getPaymentTypeInDraftList(paymentindraft);
        
        for(PaymentInInvoiceSalesDraftEntity is : islist){
            paymentininvoicesalesdraftService.deletePaymentinInvoiceSalesDraft(is.getId());
        }
        
        for(PaymentInReturnSalesDraftEntity rs : rslist){
            paymentinreturnsalesdraftService.deletePaymentInReturnSalesDraft(rs.getId());
        }
        
        for(PaymentTypeInDraftEntity ptd : ptdlist){
            paymenttypeindraftService.deletePaymentTypeInDraft(ptd.getId());
        }
        
        paymentindraftService.deletePaymentInDraft(paymentindraft.getId());
        
        return ResponseEntity.ok(paymentin.getId());
    }
}
