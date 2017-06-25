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
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
import net.purnama.pureff.entity.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeOutDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutDraftService;
import net.purnama.pureff.service.PaymentOutExpensesDraftService;
import net.purnama.pureff.service.PaymentOutExpensesService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseDraftService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseDraftService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.PaymentTypeOutDraftService;
import net.purnama.pureff.service.PaymentTypeOutService;
import net.purnama.pureff.service.RateService;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
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
public class PaymentOutDraftController {
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentOutDraftService paymentoutdraftService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    RateService rateService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    NumberingService numberingService;
    
    @Autowired
    PaymentOutInvoicePurchaseDraftService paymentoutinvoicepurchasedraftService;
    
    @Autowired
    PaymentOutReturnPurchaseDraftService paymentoutreturnpurchasedraftService;
    
    @Autowired
    PaymentOutExpensesDraftService paymentoutexpensesdraftService;
    
    @Autowired
    PaymentTypeOutDraftService paymenttypeoutdraftService;
    
    @Autowired
    PaymentOutInvoicePurchaseService paymentoutinvoicepurchaseService;
    
    @Autowired
    PaymentOutReturnPurchaseService paymentoutreturnpurchaseService;
    
    @Autowired
    PaymentOutExpensesService paymentoutexpensesService;
    
    @Autowired
    PaymentTypeOutService paymenttypeoutService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getPaymentOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentOutDraftList() {
        
        List<PaymentOutDraftEntity> ls = paymentoutdraftService.getPaymentOutDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentOutDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentoutdraftService.getPaymentOutDraft(id));
    }

    @RequestMapping(value = "api/addPaymentOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addPaymentOutDraft(HttpServletRequest httpRequest) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(16);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu outgoing payment");
        }
        
        CurrencyEntity currency = currencyService.getDefaultCurrency();
        RateEntity rate = rateService.getLastRate(currency);
        Calendar date = Calendar.getInstance();
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(IdGenerator.generateId());
        paymentoutdraft.setDate(date);
        paymentoutdraft.setDuedate(date);
        paymentoutdraft.setAmount(0);
        paymentoutdraft.setWarehouse(warehouse);
        paymentoutdraft.setNote("");
        paymentoutdraft.setLastmodified(date);
        paymentoutdraft.setLastmodifiedby(user);
        paymentoutdraft.setNumbering(numbering);
        paymentoutdraft.setCurrency(currencyService.getDefaultCurrency());
        if(rate == null){
            paymentoutdraft.setRate(1);
        }
        else{
            paymentoutdraft.setRate(rate.getValue());
        }
        
        paymentoutdraftService.addPaymentOutDraft(paymentoutdraft);
        
        return ResponseEntity.ok(paymentoutdraft.getId());
    }

    @RequestMapping(value = "api/updatePaymentOutDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentOutDraft(HttpServletRequest httpRequest,
            @RequestBody PaymentOutDraftEntity paymentoutdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentoutdraft.setLastmodified(Calendar.getInstance());
        paymentoutdraft.setWarehouse(warehouse);
        paymentoutdraft.setLastmodifiedby(user);
        
        paymentoutdraftService.updatePaymentOutDraft(paymentoutdraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "api/deletePaymentOutDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deletePaymentOutDraft(@RequestParam(value="id") String id) {
        PaymentOutDraftEntity paymentoutdraft = paymentoutdraftService.getPaymentOutDraft(id);
        
        List<PaymentOutInvoicePurchaseDraftEntity> iplist = 
                paymentoutinvoicepurchasedraftService.getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft);
        
        List<PaymentOutReturnPurchaseDraftEntity> rplist = 
                paymentoutreturnpurchasedraftService.getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
        
        List<PaymentOutExpensesDraftEntity> exlist = 
                paymentoutexpensesdraftService.getPaymentOutExpensesDraftEntityList(paymentoutdraft);
        
        List<PaymentTypeOutDraftEntity> ptdlist = 
                paymenttypeoutdraftService.getPaymentTypeOutDraftList(paymentoutdraft);
        
        for(PaymentOutInvoicePurchaseDraftEntity ip : iplist){
            paymentoutinvoicepurchasedraftService.deletePaymentOutInvoicePurchaseDraft(ip.getId());
        }
        
        for(PaymentOutReturnPurchaseDraftEntity rp : rplist){
            paymentoutreturnpurchasedraftService.deletePaymentOutReturnPurchaseDraft(rp.getId());
        }
        
        for(PaymentOutExpensesDraftEntity ex : exlist){
            paymentoutexpensesdraftService.deletePaymentOutExpensesDraft(ex.getId());
        }
        
        for(PaymentTypeOutDraftEntity ptd : ptdlist){
            paymenttypeoutdraftService.deletePaymentTypeOutDraft(ptd.getId());
        }
        
        paymentoutdraftService.deletePaymentOutDraft(id);		
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentOutDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentOutDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        List<PaymentOutDraftEntity> ls = paymentoutdraftService.
                getPaymentOutDraftList(itemperpage, page, sort, keyword, user);
        return ResponseEntity.ok(ls);
    }
    
    
    @RequestMapping(value = {"api/countPaymentOutDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> countPaymentOutDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        return ResponseEntity.ok(paymentoutdraftService.countPaymentOutDraftList(keyword, user));
    }
    
    @RequestMapping(value = "api/closePaymentOutDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closePaymentOutDraft(@RequestParam(value="id") String id) {
        
        PaymentOutDraftEntity paymentoutdraft = paymentoutdraftService.getPaymentOutDraft(id);
        
        NumberingEntity numbering = paymentoutdraft.getNumbering();
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(IdGenerator.generateId());
        paymentout.setNumber(paymentoutdraft.getNumbering().getCurrentId());
        paymentout.setDate(paymentoutdraft.getDate());
        paymentout.setPrinted(0);
        paymentout.setWarehouse(paymentoutdraft.getWarehouse());
        paymentout.setDraftid(paymentoutdraft.getId());
        paymentout.setDuedate(paymentoutdraft.getDuedate());
        paymentout.setAmount(paymentoutdraft.getAmount());
        paymentout.setCurrency(paymentoutdraft.getCurrency());
        paymentout.setRate(paymentoutdraft.getRate());
        paymentout.setPartner(paymentoutdraft.getPartner());
        paymentout.setNote(paymentoutdraft.getNote());
        paymentout.setLastmodified(Calendar.getInstance());
        paymentout.setLastmodifiedby(paymentoutdraft.getLastmodifiedby());
        paymentout.setStatus(true);
        paymentout.setCurrency_code(paymentoutdraft.getCurrency().getCode());
        paymentout.setCurrency_name(paymentoutdraft.getCurrency().getName());
        paymentout.setPartner_name(paymentoutdraft.getPartner().getName());
        paymentout.setPartner_code(paymentoutdraft.getPartner().getCode());
        paymentout.setPartner_address(paymentoutdraft.getPartner().getAddress());
        paymentout.setUser_code(paymentout.getLastmodifiedby().getCode());
        paymentout.setWarehouse_code(paymentout.getWarehouse().getCode());
        paymentoutService.addPaymentOut(paymentout);
        
        double totalbalance= 0;
        
        for(PaymentOutInvoicePurchaseDraftEntity piisd : 
                paymentoutinvoicepurchasedraftService.
                        getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft)){
            PaymentOutInvoicePurchaseEntity prs = new PaymentOutInvoicePurchaseEntity();
            prs.setId(IdGenerator.generateId());
            prs.setAmount(piisd.getAmount());
            prs.setPaymentout(paymentout);
            prs.setInvoicepurchase(piisd.getInvoicepurchase());
            
            prs.setInvoice_id(piisd.getInvoicepurchase().getNumber());
            prs.setInvoice_warehouse(piisd.getInvoicepurchase().getWarehouse_code());
            prs.setInvoice_currency(piisd.getInvoicepurchase().getCurrency_code());
            prs.setInvoice_date(piisd.getInvoicepurchase().getFormattedDate());
            prs.setInvoice_duedate(piisd.getInvoicepurchase().getFormattedDueDate());
            prs.setInvoice_total(piisd.getInvoicepurchase().getFormattedTotal_after_tax());
            
            paymentoutinvoicepurchaseService.addPaymentOutInvoicePurchase(prs);
            
            InvoicePurchaseEntity rs = piisd.getInvoicepurchase();
            rs.setPaid(rs.getPaid() + piisd.getAmount());
            invoicepurchaseService.updateInvoicePurchase(rs);
            
            totalbalance -= piisd.getAmount() * rs.getRate();
        }
        
        for(PaymentOutExpensesDraftEntity piisd : 
                paymentoutexpensesdraftService.
                        getPaymentOutExpensesDraftEntityList(paymentoutdraft)){
            PaymentOutExpensesEntity prs = new PaymentOutExpensesEntity();
            prs.setId(IdGenerator.generateId());
            prs.setAmount(piisd.getAmount());
            prs.setPaymentout(paymentout);
            prs.setExpenses(piisd.getExpenses());
            
            prs.setInvoice_id(piisd.getExpenses().getNumber());
            prs.setInvoice_warehouse(piisd.getExpenses().getWarehouse_code());
            prs.setInvoice_currency(piisd.getExpenses().getCurrency_code());
            prs.setInvoice_date(piisd.getExpenses().getFormattedDate());
            prs.setInvoice_duedate(piisd.getExpenses().getFormattedDueDate());
            prs.setInvoice_total(piisd.getExpenses().getFormattedTotal_after_tax());
            
            paymentoutexpensesService.addPaymentOutExpenses(prs);
            
            ExpensesEntity rs = piisd.getExpenses();
            rs.setPaid(rs.getPaid() + piisd.getAmount());
            expensesService.updateExpenses(rs);
            
            totalbalance -= piisd.getAmount() * rs.getRate();
        }
        
        for(PaymentOutReturnPurchaseDraftEntity pirsd : 
                paymentoutreturnpurchasedraftService.
                        getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft)){
            PaymentOutReturnPurchaseEntity prs = new PaymentOutReturnPurchaseEntity();
            prs.setId(IdGenerator.generateId());
            prs.setAmount(pirsd.getAmount());
            prs.setPaymentout(paymentout);
            prs.setReturnpurchase(pirsd.getReturnpurchase());
            
            prs.setInvoice_id(pirsd.getReturnpurchase().getNumber());
            prs.setInvoice_warehouse(pirsd.getReturnpurchase().getWarehouse_code());
            prs.setInvoice_currency(pirsd.getReturnpurchase().getCurrency_code());
            prs.setInvoice_date(pirsd.getReturnpurchase().getFormattedDate());
            prs.setInvoice_duedate(pirsd.getReturnpurchase().getFormattedDueDate());
            prs.setInvoice_total(pirsd.getReturnpurchase().getFormattedTotal_after_tax());
            
            paymentoutreturnpurchaseService.addPaymentOutReturnPurchase(prs);
            
            ReturnPurchaseEntity rs = pirsd.getReturnpurchase();
            rs.setPaid(rs.getPaid() + pirsd.getAmount());
            returnpurchaseService.updateReturnPurchase(rs);
            
            totalbalance += pirsd.getAmount() * rs.getRate();
        }
        
        for(PaymentTypeOutDraftEntity ptode : paymenttypeoutdraftService.
                getPaymentTypeOutDraftList(paymentoutdraft)){
            
            PaymentTypeOutEntity ptoe = new PaymentTypeOutEntity();
            ptoe.setAmount(ptode.getAmount());
            ptoe.setBank(ptode.getBank());
            ptoe.setDate(ptode.getDate());
            ptoe.setDuedate(ptode.getDuedate());
            ptoe.setExpirydate(ptode.getExpirydate());
            ptoe.setId(IdGenerator.generateId());
            ptoe.setLastmodified(Calendar.getInstance());
            ptoe.setLastmodifiedby(ptode.getLastmodifiedby());
            ptoe.setNote(ptode.getNote());
            ptoe.setNumber(ptode.getNumber());
            ptoe.setPaymentout(paymentout);
            ptoe.setStatus(false);
            ptoe.setValid(true);
            ptoe.setType(ptode.getType());
            
            paymenttypeoutService.addPaymentTypeOut(ptoe);
            
            PartnerEntity partner = paymentout.getPartner();
        
            if(partner.getPartnertype().getParent() == GlobalFields.CUSTOMER){
                partner.setBalance(partner.getBalance() - (paymentout.getAmount() * paymentout.getRate()));
            }
            else{
                double remaining = paymentout.getAmount() + totalbalance;
                partner.setBalance(partner.getBalance() + totalbalance - remaining);
            }
            partnerService.updatePartner(partner);
            
            numbering.setCurrent(numbering.getCurrent()+1);
        
            if(numbering.getCurrent() != numbering.getEnd()){

            }
            else{
                numbering.setStatus(false);
            }

            numberingService.updateNumbering(numbering);
        }
        
        List<PaymentOutInvoicePurchaseDraftEntity> iplist = 
                paymentoutinvoicepurchasedraftService.getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft);
        
        List<PaymentOutReturnPurchaseDraftEntity> rplist = 
                paymentoutreturnpurchasedraftService.getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
        
        List<PaymentOutExpensesDraftEntity> exlist = 
                paymentoutexpensesdraftService.getPaymentOutExpensesDraftEntityList(paymentoutdraft);
        
        List<PaymentTypeOutDraftEntity> ptdlist = 
                paymenttypeoutdraftService.getPaymentTypeOutDraftList(paymentoutdraft);
        
        for(PaymentOutInvoicePurchaseDraftEntity ip : iplist){
            paymentoutinvoicepurchasedraftService.deletePaymentOutInvoicePurchaseDraft(ip.getId());
        }
        
        for(PaymentOutReturnPurchaseDraftEntity rp : rplist){
            paymentoutreturnpurchasedraftService.deletePaymentOutReturnPurchaseDraft(rp.getId());
        }
        
        for(PaymentOutExpensesDraftEntity ex : exlist){
            paymentoutexpensesdraftService.deletePaymentOutExpensesDraft(ex.getId());
        }
        
        for(PaymentTypeOutDraftEntity ptd : ptdlist){
            paymenttypeoutdraftService.deletePaymentTypeOutDraft(ptd.getId());
        }
        
        paymentoutdraftService.deletePaymentOutDraft(id);	
        
        return ResponseEntity.ok(paymentout.getId());
    }
}

