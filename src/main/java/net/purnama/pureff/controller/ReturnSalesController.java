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
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.ItemReturnSalesService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PaymentInReturnSalesService;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.ItemReturnSalesTableModel;
import net.purnama.pureff.tablemodel.ReturnSalesTableModel2;
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
    
    @Autowired
    CurrencyService currencyService;
    
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
        else if(!returnsales.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
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
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getReturnSalesList(
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
        
        List<ReturnSalesEntity> ls = returnsalesService.getReturnSalesList(
                CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end),
                warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getReturnSalesPrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getReturnSalesPrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        ReturnSalesEntity returnsales = returnsalesService.getReturnSales(id);
        
        List<ItemReturnSalesEntity> list = itemreturnsalesService.getItemReturnSalesList(returnsales);
        
        HashMap map = new HashMap();
        map.put("DATE", returnsales.getFormatteddate());
        map.put("ID", returnsales.getNumber());
        map.put("CURRENCY", returnsales.getCurrency_code());
        map.put("WAREHOUSE", returnsales.getWarehouse_code());
        map.put("NOTE", returnsales.getNote());
        map.put("DUEDATE", returnsales.getFormattedduedate());
        map.put("PARTNER", returnsales.getPartner_name());
        map.put("ADDRESS", returnsales.getPartner_address());
        map.put("SUBTOTAL", returnsales.getFormattedsubtotal());
        map.put("DISCOUNT", returnsales.getFormatteddiscount());
        map.put("ROUNDING", returnsales.getFormattedrounding());
        map.put("FREIGHT", returnsales.getFormattedfreight());
        map.put("TAX", returnsales.getFormattedtax());
        map.put("SAID", IndonesianNumberConvertion.numberToSaid(returnsales.getTotal_after_tax()));
        map.put("TOTAL", returnsales.getFormattedtotal_after_tax());
        map.put("RATE", returnsales.getFormattedrate());    

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/ReturnSales.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        ItemReturnSalesTableModel iistm = new ItemReturnSalesTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(iistm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=ReturnSales-"+ returnsales.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = {"api/getReturnSalesRecapReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getReturnSalesRecapReport(
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
        
        List<ReturnSalesEntity> returnsaleslist = returnsalesService.
                getReturnSalesList(start, end, warehouse, partner, currency, status);
        
        double total = 0;
                        
        for(ReturnSalesEntity invoice : returnsaleslist){
            total += invoice.getTotal_after_tax();
        }
        
        ReturnSalesTableModel2 istm = new ReturnSalesTableModel2(returnsaleslist);
                        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("CURRENCY", currency.getCode());
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        map.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        map.put("NUMOFINVOICES", String.valueOf(returnsaleslist.size()));
        map.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/ReturnSalesRecapReport.jasper");

        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(istm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        
        responseHeaders.add("content-disposition", "attachment; filename=ReturnSalesRecap.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
