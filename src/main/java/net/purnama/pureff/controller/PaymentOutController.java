/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.convertion.IndonesianNumberConvertion;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
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
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutExpensesService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.PaymentTypeOutService;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.PaymentOutTableModel2;
import net.purnama.pureff.tablemodel.PaymentTypeOutTableModel;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.GlobalFields;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class PaymentOutController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @Autowired
    PaymentOutInvoicePurchaseService paymentoutinvoicepurchaseService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @Autowired
    PaymentOutReturnPurchaseService paymentoutreturnpurchaseService;
    
    @Autowired
    ExpensesService expensesService;
    
    @Autowired
    PaymentOutExpensesService paymentoutexpensesService;
    
    @Autowired
    PaymentTypeOutService paymenttypeoutService;
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getPaymentOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentOutList() {
        
        List<PaymentOutEntity> ls = paymentoutService.getPaymentOutList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOut", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentOut(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentoutService.getPaymentOut(id));
    }

    @RequestMapping(value = "api/updatePaymentOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentOut(HttpServletRequest httpRequest,
            @RequestBody PaymentOutEntity paymentout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentout.setLastmodified(Calendar.getInstance());
        paymentout.setWarehouse(warehouse);
        paymentout.setLastmodifiedby(user);
        
        paymentoutService.updatePaymentOut(paymentout);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentOutList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PaymentOutEntity> ls = paymentoutService.
                getPaymentOutList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPaymentOutList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(paymentoutService.countPaymentOutList(keyword));
    }
    
    @RequestMapping(value = "api/cancelPaymentOut", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelPaymentOut(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        
        double totalbalance= 0;
        
        PaymentOutEntity paymentout = paymentoutService.getPaymentOut(id);
        
        if(!paymentout.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
        }
        
        List<PaymentOutInvoicePurchaseEntity> poislist = 
                paymentoutinvoicepurchaseService.getPaymentOutInvoicePurchaseEntityList(paymentout);
        
        for(PaymentOutInvoicePurchaseEntity pois : poislist){
            
            InvoicePurchaseEntity invoicepurchase = pois.getInvoicepurchase();
            
            invoicepurchase.setPaid(invoicepurchase.getPaid() - pois.getAmount());
            
            invoicepurchaseService.updateInvoicePurchase(invoicepurchase);
            
            totalbalance += pois.getAmount() * invoicepurchase.getRate();
            
            pois.setInvoicepurchase(null);
            
            paymentoutinvoicepurchaseService.updatePaymentOutInvoicePurchase(pois);
        }
        
        List<PaymentOutReturnPurchaseEntity> porslist = 
                paymentoutreturnpurchaseService.getPaymentOutReturnPurchaseEntityList(paymentout);
        
        for(PaymentOutReturnPurchaseEntity pors : porslist){
            
            ReturnPurchaseEntity returnpurchase = pors.getReturnpurchase();
            
            returnpurchase.setPaid(returnpurchase.getPaid() - pors.getAmount());
            
            returnpurchaseService.updateReturnPurchase(returnpurchase);
            
            totalbalance -= pors.getAmount() * returnpurchase.getRate();
            
            pors.setReturnpurchase(null);
            
            paymentoutreturnpurchaseService.updatePaymentOutReturnPurchase(pors);
        }
        
        List<PaymentOutExpensesEntity> poeelist = 
                paymentoutexpensesService.getPaymentOutExpensesEntityList(paymentout);
        
        for(PaymentOutExpensesEntity poee : poeelist){
            
            ExpensesEntity expenses = poee.getExpenses();
            
            expenses.setPaid(expenses.getPaid() - poee.getAmount());
            
            expensesService.updateExpenses(expenses);
            
            totalbalance += poee.getAmount() * expenses.getRate();
            
            poee.setExpenses(null);
            
            paymentoutexpensesService.updatePaymentOutExpenses(poee);
        }
        
        for(PaymentTypeOutEntity ptde : paymenttypeoutService.
                getPaymentTypeOutList(paymentout)){
            ptde.setStatus(false);
            ptde.setValid(false);
            
            paymenttypeoutService.updatePaymentTypeOut(ptde);
        }
        
        paymentout.setStatus(false);
        paymentout.setLastmodified(Calendar.getInstance());
        paymentout.setLastmodifiedby(user);
        
        paymentoutService.updatePaymentOut(paymentout);
        
        PartnerEntity partner = paymentout.getPartner();
        if(partner.getPartnertype().getParent() == GlobalFields.CUSTOMER){
            partner.setBalance(partner.getBalance() + (paymentout.getAmount() * paymentout.getRate()));
        }
        else{
            double remaining = paymentout.getAmount() - totalbalance;
            partner.setBalance(partner.getBalance() + totalbalance + remaining);
        }
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getPaymentOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getPaymentOutList(
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
        
        List<PaymentOutEntity> ls = paymentoutService.
                getPaymentOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getPaymentOutPrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentOutPrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        PaymentOutEntity paymentout = paymentoutService.getPaymentOut(id);
        
        List<PaymentTypeOutEntity> list = paymenttypeoutService.getPaymentTypeOutList(paymentout);
        
        HashMap map = new HashMap();
        map.put("DATE", paymentout.getFormatteddate());
        map.put("ID", paymentout.getNumber());
        map.put("CURRENCY", paymentout.getCurrency_code());
        map.put("WAREHOUSE", paymentout.getWarehouse_code());
        map.put("NOTE", paymentout.getNote());
        map.put("PARTNER", paymentout.getPartner_name());
        map.put("ADDRESS", paymentout.getPartner_address());
        map.put("SAID", IndonesianNumberConvertion.numberToSaid(paymentout.getAmount()));
        map.put("RATE", paymentout.getFormattedrate());
        map.put("AMOUNT", paymentout.getFormattedamount());

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/PaymentOut.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        PaymentTypeOutTableModel ptitm = new PaymentTypeOutTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(ptitm));
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        responseHeaders.add("content-disposition", "attachment; filename=PaymentOut-"+ paymentout.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = {"api/getPaymentOutRecapReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getPaymentOutRecapReport(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="status") boolean status) throws IOException, JRException{
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        WarehouseEntity warehouse = warehouseService.getWarehouse(warehouseid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = currencyService.getCurrency(currencyid);
        
        List<PaymentOutEntity> paymentoutlist = paymentoutService.
                getPaymentOutList(start, end, warehouse, partner, currency, status);
        
        double total = 0;
                        
        for(PaymentOutEntity invoice : paymentoutlist){
            total += invoice.getAmount();
        }
        
        PaymentOutTableModel2 istm = new PaymentOutTableModel2(paymentoutlist);
                        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("CURRENCY", currency.getCode());
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        map.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        map.put("NUMOFINVOICES", String.valueOf(paymentoutlist.size()));
        map.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/PaymentOutRecapReport.jasper");

        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(istm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        
        responseHeaders.add("content-disposition", "attachment; filename=PaymentOutRecap.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
