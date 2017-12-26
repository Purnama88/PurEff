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
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
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
public class ItemInvoiceWarehouseOutController {
    @Autowired
    ItemInvoiceWarehouseOutService iteminvoicewarehouseoutService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @RequestMapping(value = "api/getItemInvoiceWarehouseOutList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceWarehouseOutList(@RequestParam(value="id") String id) {
        InvoiceWarehouseOutEntity ad = invoicewarehouseoutService.getInvoiceWarehouseOut(id);
        
        return ResponseEntity.ok(iteminvoicewarehouseoutService.getItemInvoiceWarehouseOutList(ad));
    }
    
    @RequestMapping(value = {"api/getItemInvoiceWarehouseOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "destinationid", "status"})
    public ResponseEntity<?> getItemInvoiceWarehouseOutList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="destinationid") String destinationid,
            @RequestParam(value="status") boolean status){
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        WarehouseEntity destination = new WarehouseEntity();
        destination.setId(destinationid);
        
        List<ItemInvoiceWarehouseOutEntity> ls = iteminvoicewarehouseoutService.
                getItemInvoiceWarehouseOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, destination, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getInvoiceWarehouseOutDetailReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "destinationid", "status"})
    public ResponseEntity<?> getInvoiceWarehouseOutDetailReport(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="destinationid") String destinationid,
            @RequestParam(value="status") boolean status) throws IOException, JRException{
        
        WarehouseEntity warehouse = warehouseService.getWarehouse(warehouseid);
        WarehouseEntity destination = warehouseService.getWarehouse(destinationid);
        
        List<ItemInvoiceWarehouseOutEntity> ls = iteminvoicewarehouseoutService.
                getItemInvoiceWarehouseOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, destination, status);
        
        JRBeanCollectionDataSource beanColDataSource =
                            new JRBeanCollectionDataSource(ls);

        Map parameters = new HashMap();
                        
        parameters.put("WAREHOUSE", warehouse.getCode());
        parameters.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        parameters.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        parameters.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
                        
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/InvoiceWarehouseOutDetailReport.jasper");
                          
        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jr, parameters, beanColDataSource);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        responseHeaders.add("content-disposition", "attachment; filename=InvoiceWarehouseOutDetail.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
