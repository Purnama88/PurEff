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
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ItemReturnPurchaseService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentOutReturnPurchaseService;
import net.purnama.pureff.service.ReturnPurchaseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.ItemReturnPurchaseTableModel;
import net.purnama.pureff.tablemodel.ReturnPurchaseTableModel2;
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
    CurrencyService currencyService;
    
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
        else if(!returnpurchase.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
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
        
        List<ReturnPurchaseEntity> ls = returnpurchaseService.getReturnPurchaseList(
                CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end),
                warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getReturnPurchasePrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnPurchasePrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        ReturnPurchaseEntity returnpurchase = returnpurchaseService.getReturnPurchase(id);
        
        List<ItemReturnPurchaseEntity> list = itemreturnpurchaseService.getItemReturnPurchaseList(returnpurchase);
        
        HashMap map = new HashMap();
        map.put("DATE", returnpurchase.getFormatteddate());
        map.put("ID", returnpurchase.getNumber());
        map.put("CURRENCY", returnpurchase.getCurrency_code());
        map.put("WAREHOUSE", returnpurchase.getWarehouse_code());
        map.put("NOTE", returnpurchase.getNote());
        map.put("DUEDATE", returnpurchase.getFormattedduedate());
        map.put("PARTNER", returnpurchase.getPartner_name());
        map.put("ADDRESS", returnpurchase.getPartner_address());
        map.put("SUBTOTAL", returnpurchase.getFormattedsubtotal());
        map.put("DISCOUNT", returnpurchase.getFormatteddiscount());
        map.put("ROUNDING", returnpurchase.getFormattedrounding());
        map.put("FREIGHT", returnpurchase.getFormattedfreight());
        map.put("TAX", returnpurchase.getFormattedtax());
        map.put("SAID", IndonesianNumberConvertion.numberToSaid(returnpurchase.getTotal_after_tax()));
        map.put("TOTAL", returnpurchase.getFormattedtotal_after_tax());
        map.put("RATE", returnpurchase.getFormattedrate());    

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/ReturnPurchase.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        ItemReturnPurchaseTableModel iistm = new ItemReturnPurchaseTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(iistm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=ReturnPurchase-"+ returnpurchase.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = {"api/getReturnPurchaseRecapReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getReturnPurchaseRecapReport(
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
        
        List<ReturnPurchaseEntity> returnpurchaselist = returnpurchaseService.
                getReturnPurchaseList(start, end, warehouse, partner, currency, status);
        
        double total = 0;
                        
        for(ReturnPurchaseEntity invoice : returnpurchaselist){
            total += invoice.getTotal_after_tax();
        }
        
        ReturnPurchaseTableModel2 istm = new ReturnPurchaseTableModel2(returnpurchaselist);
                        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("CURRENCY", currency.getCode());
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        map.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        map.put("NUMOFINVOICES", String.valueOf(returnpurchaselist.size()));
        map.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/ReturnPurchaseRecapReport.jasper");

        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(istm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        
        responseHeaders.add("content-disposition", "attachment; filename=ReturnPurchaseRecap.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
