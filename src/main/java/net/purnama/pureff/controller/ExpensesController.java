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
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.ItemExpensesService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutExpensesService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.ExpensesTableModel2;
import net.purnama.pureff.tablemodel.ItemExpensesTableModel;
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
public class ExpensesController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ExpensesService expensesService;
    
    @Autowired
    ItemExpensesService itemexpensesService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    CurrencyService currencyService;
    
    @Autowired
    PaymentOutExpensesService paymentoutexpensesService;
    
    @RequestMapping(value = "api/getExpensesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getExpensesList() {
        
        List<ExpensesEntity> ls = expensesService.getExpensesList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getExpenses", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getExpenses(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(expensesService.getExpenses(id));
    }

    @RequestMapping(value = "api/updateExpenses", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateExpenses(HttpServletRequest httpRequest,
            @RequestBody ExpensesEntity expenses) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        expenses.setLastmodified(Calendar.getInstance());
        expenses.setWarehouse(warehouse);
        expenses.setLastmodifiedby(user);
        
        expensesService.updateExpenses(expenses);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getExpensesList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getExpensesList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<ExpensesEntity> ls = expensesService.
                getExpensesList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countExpensesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countExpensesList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(expensesService.countExpensesList(keyword));
    }
    
    @RequestMapping(value = {"api/closeExpenses"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeExpensesList(
            @RequestParam(value="id") String id){
        
        ExpensesEntity expenses = expensesService.getExpenses(id);
        
        expenses.setPaid(expenses.getTotal_after_tax());
        
        expensesService.updateExpenses(expenses);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getUnpaidExpensesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"partnerid", "currencyid"})
    public ResponseEntity<?> getUnpaidExpensesList(
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid
            ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        return ResponseEntity.ok(expensesService.getUnpaidExpensesList(partner, currency));
    }
    
    @RequestMapping(value = {"api/cancelExpenses"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelExpenses(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        ExpensesEntity expenses = expensesService.getExpenses(id);
        
        List ls = paymentoutexpensesService.getPaymentOutExpensesEntityList(expenses);
        
        if(!ls.isEmpty()){
            return ResponseEntity.badRequest().body("You have some payment associated with this invoice");
        }
        else if(!expenses.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        
        expenses.setLastmodified(Calendar.getInstance());
        expenses.setLastmodifiedby(user);
        expenses.setStatus(false);
        
        expensesService.updateExpenses(expenses);
        
        PartnerEntity partner = expenses.getPartner();
        partner.setBalance(partner.getBalance() - expenses.getTotal_defaultcurrency());
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getExpensesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getExpensesList(
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
        
        List<ExpensesEntity> ls = expensesService.getExpensesList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, 
                partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getExpensesPrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getExpensesPrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        ExpensesEntity expenses = expensesService.getExpenses(id);
        
        List<ItemExpensesEntity> list = itemexpensesService.getItemExpensesList(expenses);
        
        HashMap map = new HashMap();
        map.put("DATE", expenses.getFormatteddate());
        map.put("ID", expenses.getNumber());
        map.put("CURRENCY", expenses.getCurrency_code());
        map.put("WAREHOUSE", expenses.getWarehouse_code());
        map.put("NOTE", expenses.getNote());
        map.put("DUEDATE", expenses.getFormattedduedate());
        map.put("PARTNER", expenses.getPartner_name());
        map.put("ADDRESS", expenses.getPartner_address());
        map.put("SUBTOTAL", expenses.getFormattedsubtotal());
        map.put("DISCOUNT", expenses.getFormatteddiscount());
        map.put("ROUNDING", expenses.getFormattedrounding());
        map.put("FREIGHT", expenses.getFormattedfreight());
        map.put("TAX", expenses.getFormattedtax());
        map.put("SAID", IndonesianNumberConvertion.numberToSaid(expenses.getTotal_after_tax()));
        map.put("TOTAL", expenses.getFormattedtotal_after_tax());
        map.put("RATE", expenses.getFormattedrate());    

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/Expenses.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        ItemExpensesTableModel iistm = new ItemExpensesTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(iistm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=Expenses-"+ expenses.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = {"api/getExpensesRecapReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getExpensesRecapReport(
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
        
        List<ExpensesEntity> expenseslist = expensesService.
                getExpensesList(start, end, warehouse, partner, currency, status);
        
        double total = 0;
                        
        for(ExpensesEntity invoice : expenseslist){
            total += invoice.getTotal_after_tax();
        }
        
        ExpensesTableModel2 istm = new ExpensesTableModel2(expenseslist);
                        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("CURRENCY", currency.getCode());
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        map.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        map.put("NUMOFINVOICES", String.valueOf(expenseslist.size()));
        map.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/ExpensesRecapReport.jasper");

        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(istm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        
        responseHeaders.add("content-disposition", "attachment; filename=ExpensesRecap.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
