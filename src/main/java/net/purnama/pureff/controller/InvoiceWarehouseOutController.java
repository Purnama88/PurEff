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
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.convertion.IndonesianNumberConvertion;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.ItemInvoiceWarehouseOutTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InvoiceWarehouseOutController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    InvoiceWarehouseOutService invoicewarehouseoutService;
    
    @Autowired
    ItemInvoiceWarehouseOutService iteminvoicewarehouseoutService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "api/getInvoiceWarehouseOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getInvoiceWarehouseOutList() {
        
        List<InvoiceWarehouseOutEntity> ls = invoicewarehouseoutService.getInvoiceWarehouseOutList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getInvoiceWarehouseOut", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseOut(@RequestParam(value="id") String id){
        return ResponseEntity.ok(invoicewarehouseoutService.getInvoiceWarehouseOut(id));
    }

    @RequestMapping(value = "api/updateInvoiceWarehouseOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateInvoiceWarehouseOut(HttpServletRequest httpRequest,
            @RequestBody InvoiceWarehouseOutEntity invoicewarehoseout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        invoicewarehoseout.setLastmodified(Calendar.getInstance());
        invoicewarehoseout.setWarehouse(warehouse);
        invoicewarehoseout.setLastmodifiedby(user);
        
        invoicewarehouseoutService.updateInvoiceWarehouseOut(invoicewarehoseout);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getInvoiceWarehouseOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getInvoiceWarehouseOutList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<InvoiceWarehouseOutEntity> ls = invoicewarehouseoutService.
                getInvoiceWarehouseOutList(itemperpage, page, sort, keyword);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countInvoiceWarehouseOutList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countInvoiceWarehouseOutList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(invoicewarehouseoutService.countInvoiceWarehouseOutList(keyword));
    }
    
    @RequestMapping(value = {"api/cancelInvoiceWarehouseOut"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> cancelInvoiceWarehouseOut(HttpServletRequest httpRequest,
            @RequestParam(value="id") String id){
        InvoiceWarehouseOutEntity invoicewarehouseout = invoicewarehouseoutService.getInvoiceWarehouseOut(id);
        
        if(!invoicewarehouseout.isStatus()){
            return ResponseEntity.badRequest().body("This invoice is already cancelled");
        }
        
        List<ItemInvoiceWarehouseOutEntity> iislist = 
                iteminvoicewarehouseoutService.getItemInvoiceWarehouseOutList(invoicewarehouseout);
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = invoicewarehouseout.getWarehouse();
        
        invoicewarehouseout.setLastmodified(Calendar.getInstance());
        invoicewarehouseout.setLastmodifiedby(user);
        invoicewarehouseout.setStatus(false);
        
        for(ItemInvoiceWarehouseOutEntity iis : iislist){
            
            ItemEntity item = iis.getItem();
            
            ItemWarehouseEntity iw = itemwarehouseService.getItemWarehouse(
                    warehouse, item);
            
            iw.setStock(iw.getStock() + iis.getBasequantity());
            
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        invoicewarehouseout.setStatus(false);
        invoicewarehouseout.setLastmodified(Calendar.getInstance());
        invoicewarehouseout.setLastmodifiedby(user);
        
        invoicewarehouseoutService.updateInvoiceWarehouseOut(invoicewarehouseout);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = {"api/getInvoiceWarehouseOutPrintPage"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getInvoiceWarehouseOutPrintPage(
            HttpServletRequest httpRequest,
            @RequestParam(value="id") String id) throws IOException, JRException{
        
        InvoiceWarehouseOutEntity invoicewarehouseout = invoicewarehouseoutService.getInvoiceWarehouseOut(id);
        
        List<ItemInvoiceWarehouseOutEntity> list = iteminvoicewarehouseoutService.getItemInvoiceWarehouseOutList(invoicewarehouseout);
        
        HashMap map = new HashMap();
        map.put("DATE", invoicewarehouseout.getFormatteddate());
        map.put("ID", invoicewarehouseout.getNumber());
        map.put("NOTE", invoicewarehouseout.getNote());
        map.put("ORIGIN", invoicewarehouseout.getWarehouse_code());
        map.put("DESTINATION", invoicewarehouseout.getDestination_code());  

        ClassLoader cldr = this.getClass().getClassLoader();
            URL imageURL = cldr.getResource("net/purnama/template/InventoryTransfer.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        ItemInvoiceWarehouseOutTableModel iistm = new ItemInvoiceWarehouseOutTableModel(list);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                map,
                new JRTableModelDataSource(iistm));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=InvoiceWarehouseOut-"+ invoicewarehouseout.getNumber() +".pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}