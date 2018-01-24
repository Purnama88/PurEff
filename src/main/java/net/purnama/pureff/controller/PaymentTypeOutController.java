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
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.CurrencyService;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.PaymentTypeOutService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.GlobalFields;
import net.purnama.pureff.util.IdGenerator;
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
public class PaymentTypeOutController {
    
    @Autowired
    PaymentTypeOutService paymenttypeoutService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    CurrencyService currencyService;
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"paymentid"})
    public ResponseEntity<?> getPaymentTypeOutList(@RequestParam(value="paymentid") String paymentid) {
        
        PaymentOutEntity paymentout = paymentoutService.getPaymentOut(paymentid);
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPaymentTypeOutList(paymentout);
        return ResponseEntity.ok(ls);
    }
    
//    @RequestMapping(value = "api/getPendingPaymentTypeOutList", method = RequestMethod.GET, 
//            headers = "Accept=application/json", params = {"type"})
//    public ResponseEntity<?> getPendingPaymentTypeOutList(@RequestParam(value="type") int type) {
//        
//        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPendingPaymentTypeOutList(type);
//        return ResponseEntity.ok(ls);
//    }
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"startdate", "enddate", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeOutList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.getPaymentTypeOutList(type, accepted,
                valid, CalendarUtil.toStartOfDay(start), CalendarUtil.toEndOfDay(end));
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentTypeOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {
                "startdate", "enddate", "warehouseid", "partnerid",
                "currencyid", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeOutList(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.
                getPaymentTypeOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, type,
                        valid, accepted
                );
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/updatePaymentTypeOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentTypeOut(HttpServletRequest httpRequest,
            @RequestBody PaymentTypeOutEntity paymenttypeout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        paymenttypeout.setLastmodified(Calendar.getInstance());
        paymenttypeout.setLastmodifiedby(user);
        
        paymenttypeoutService.updatePaymentTypeOut(paymenttypeout);
        
        return ResponseEntity.ok(paymenttypeout);
    }
    
    @RequestMapping(value = "api/savePaymentTypeOutList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> savePaymentTypeOutList(
            HttpServletRequest httpRequest,
            @RequestBody List<PaymentTypeOutEntity> paymenttypeoutlist) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        for(PaymentTypeOutEntity paymenttypeout : paymenttypeoutlist){
            paymenttypeout.setLastmodifiedby(temp);
            if(paymenttypeout.getId() != null){
                paymenttypeoutService.updatePaymentTypeOut(paymenttypeout);
            }
            else{
                paymenttypeout.setId(IdGenerator.generateId());
                paymenttypeoutService.addPaymentTypeOut(paymenttypeout);
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getPaymentTypeOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"
            })
    public ResponseEntity<?> getPaymentTypeOutList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="status") boolean status
    ){
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(currencyid);
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.
                getPaymentTypeOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status
                );
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getPaymentOutDetailReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid", "currencyid", "status"})
    public ResponseEntity<?> getPaymentTypeDetailReport(
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
        
        List<PaymentTypeOutEntity> ls = paymenttypeoutService.
                getPaymentTypeOutList(CalendarUtil.toStartOfDay(start), 
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
        URL imageURL = cldr.getResource("net/purnama/template/PaymentOutDetailReport.jasper");
                          
        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jr, parameters, beanColDataSource);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        responseHeaders.add("content-disposition", "attachment; filename=PaymentOutDetail.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
    
    @RequestMapping(value = "api/getPaymentTypeOutReport", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {
                "startdate", "enddate", "warehouseid", "partnerid",
                "currencyid", "accepted", "valid", "type"})
    public ResponseEntity<?> getPaymentTypeOutReport(
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="partnerid") String partnerid,
            @RequestParam(value="currencyid") String currencyid,
            @RequestParam(value="accepted") boolean accepted,
            @RequestParam(value="valid") boolean valid,
            @RequestParam(value="type") int type) throws JRException, IOException{
        
        PartnerEntity partner = new PartnerEntity();
        partner.setId(partnerid);
        
        WarehouseEntity warehouse =  warehouseService.getWarehouse(warehouseid);
        
        CurrencyEntity currency = currencyService.getCurrency(currencyid);
        
        List<PaymentTypeOutEntity> list = paymenttypeoutService.
                getPaymentTypeOutList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, type,
                        valid, accepted);
        
        double total = 0;
                        
        for(PaymentTypeOutEntity invoice : list){
            total += invoice.getAmount();
        }

        JRBeanCollectionDataSource beanColDataSource =
            new JRBeanCollectionDataSource(list);

        Map parameters = new HashMap();

        parameters.put("WAREHOUSE", warehouse.getCode());
        parameters.put("START", GlobalFields.DATEFORMAT.format(start.getTime()));
        parameters.put("END", GlobalFields.DATEFORMAT.format(end.getTime()));
        parameters.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        parameters.put("CURRENCY", currency.getCode());
        parameters.put("NUMOFINVOICES", String.valueOf(list.size()));
        parameters.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/PaymentTypeOutReport.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
        jr, parameters, beanColDataSource);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        responseHeaders.add("content-disposition", "attachment; filename=PaymentTypeOut.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
