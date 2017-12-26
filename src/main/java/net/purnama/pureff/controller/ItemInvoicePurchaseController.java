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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.InvoicePurchaseService;
import net.purnama.pureff.service.ItemInvoicePurchaseService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.GlobalFields;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class ItemInvoicePurchaseController {
    @Autowired
    ItemInvoicePurchaseService iteminvoicepurchaseService;
    
    @Autowired
    InvoicePurchaseService invoicepurchaseService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getItemInvoicePurchaseList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoicePurchaseList(@RequestParam(value="id") String id) {
        InvoicePurchaseEntity ad = invoicepurchaseService.getInvoicePurchase(id);
        
        return ResponseEntity.ok(iteminvoicepurchaseService.getItemInvoicePurchaseList(ad));
    }
    
    @RequestMapping(value = {"api/getItemInvoicePurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getItemInvoicePurchaseList(
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
        
        List<ItemInvoicePurchaseEntity> ls = iteminvoicepurchaseService.
                getItemInvoicePurchaseList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getInvoicePurchaseDetailReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getInvoicePurchaseDetailReport(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="status") boolean status) throws IOException, JRException{
        
        WarehouseEntity warehouse = warehouseService.getWarehouse(warehouseid);
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        CurrencyEntity currency = currencyService.getCurrency(currencyid);
        
        List<ItemInvoicePurchaseEntity> ls = iteminvoicepurchaseService.
                getItemInvoicePurchaseList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        JRBeanCollectionDataSource beanColDataSource =
                            new JRBeanCollectionDataSource(ls);

        Map parameters = new HashMap();
                        
        parameters.put("WAREHOUSE", warehouse.getCode());
        parameters.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        parameters.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        parameters.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        parameters.put("CURRENCY", currency.getCode());
                        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/InvoicePurchaseDetailReport.jasper");
                          
        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jr, parameters, beanColDataSource);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        responseHeaders.add("content-disposition", "attachment; filename=InvoicePurchaseDetail.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
