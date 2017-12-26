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
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.ItemInvoicePurchaseService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutInvoicePurchaseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.InvoicePurchaseTableModel2;
import net.purnama.pureff.tablemodel.ItemInvoicePurchaseTableModel;
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
    CurrencyService currencyService;
    
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
    
    @RequestMapping(value = {"api/closeInvoicePurchase"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeInvoicePurchaseList(
            @RequestParam(value="id") String id){
        
        InvoicePurchaseEntity invoicepurchase = invoicepurchaseService.getInvoicePurchase(id);
        
        invoicepurchase.setPaid(invoicepurchase.getTotal_after_tax());
        
        invoicepurchaseService.updateInvoicePurchase(invoicepurchase);
        
        return ResponseEntity.ok("");
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
        else if(!invoicepurchase.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = invoicepurchase.getWarehouse();
        
        invoicepurchase.setLastmodified(Calendar.getInstance());
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
    
    @RequestMapping(value = {"api/getInvoicePurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getInvoicePurchaseList(
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
        
        List<InvoicePurchaseEntity> ls = invoicepurchaseService.getInvoicePurchaseList(
                CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end),
                warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getInvoicePurchasePrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoicePurchasePrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        InvoicePurchaseEntity invoicepurchase = invoicepurchaseService.getInvoicePurchase(id);
        
        List<ItemInvoicePurchaseEntity> list = iteminvoicepurchaseService.getItemInvoicePurchaseList(invoicepurchase);
        
        HashMap map = new HashMap();
        map.put("DATE", invoicepurchase.getFormatteddate());
        map.put("ID", invoicepurchase.getNumber());
        map.put("CURRENCY", invoicepurchase.getCurrency_code());
        map.put("WAREHOUSE", invoicepurchase.getWarehouse_code());
        map.put("NOTE", invoicepurchase.getNote());
        map.put("DUEDATE", invoicepurchase.getFormattedduedate());
        map.put("PARTNER", invoicepurchase.getPartner_name());
        map.put("ADDRESS", invoicepurchase.getPartner_address());
        map.put("SUBTOTAL", invoicepurchase.getFormattedsubtotal());
        map.put("DISCOUNT", invoicepurchase.getFormatteddiscount());
        map.put("ROUNDING", invoicepurchase.getFormattedrounding());
        map.put("FREIGHT", invoicepurchase.getFormattedfreight());
        map.put("TAX", invoicepurchase.getFormattedtax());
        map.put("SAID", IndonesianNumberConvertion.numberToSaid(invoicepurchase.getTotal_after_tax()));
        map.put("TOTAL", invoicepurchase.getFormattedtotal_after_tax());
        map.put("RATE", invoicepurchase.getFormattedrate());    

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/InvoicePurchase.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        ItemInvoicePurchaseTableModel iistm = new ItemInvoicePurchaseTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(iistm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=InvoicePurchase-"+ invoicepurchase.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = {"api/getInvoicePurchaseRecapReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getInvoicePurchaseRecapReport(
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
        
        List<InvoicePurchaseEntity> invoicepurchaselist = invoicepurchaseService.
                getInvoicePurchaseList(start, end, warehouse, partner, currency, status);
        
        double total = 0;
                        
        for(InvoicePurchaseEntity invoice : invoicepurchaselist){
            total += invoice.getTotal_after_tax();
        }
        
        InvoicePurchaseTableModel2 istm = new InvoicePurchaseTableModel2(invoicepurchaselist);
                        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("CURRENCY", currency.getCode());
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        map.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        map.put("NUMOFINVOICES", String.valueOf(invoicepurchaselist.size()));
        map.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/InvoicePurchaseRecapReport.jasper");

        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(istm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        
        responseHeaders.add("content-disposition", "attachment; filename=InvoicePurchaseRecap.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
