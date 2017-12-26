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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.ItemGroupService;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.tablemodel.ItemWarehouseTableModel;
import net.purnama.pureff.util.GlobalFields;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class ItemWarehouseController {
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    ItemGroupService itemgroupService;
    
    @RequestMapping(value = "api/getItemWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getItemWarehouseList() {
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.getItemWarehouseList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getItemWarehouse", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemid"})
    public ResponseEntity<?> getItemWarehouse(HttpServletRequest httpRequest,
            @RequestParam(value="itemid") String itemid) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        ItemEntity item = itemService.getItem(itemid);
        
        return ResponseEntity.ok(itemwarehouseService.getItemWarehouse(warehouse, item));
    }
    
    @RequestMapping(value = "/api/getItemWarehouseList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getItemWarehouseList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.getItemWarehouseList(warehouse,
                itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countItemWarehouseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countItemWarehouseList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        return ResponseEntity.ok(itemwarehouseService.countItemWarehouseList(warehouse, keyword));
    }
    
    @RequestMapping(value = {"api/getItemWarehouseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {
                "warehouseid", "itemgroupid", "status"})
    public ResponseEntity<?> getItemWarehouseList(
            HttpServletRequest httpRequest,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="itemgroupid") String itemgroupid,
            @RequestParam(value="status") boolean status){
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        ItemGroupEntity itemgroup = new ItemGroupEntity();
        itemgroup.setId(itemgroupid);
        
        List<ItemWarehouseEntity> ls = itemwarehouseService.
                getItemWarehouseList( warehouse, itemgroup, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getItemStockReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {
                "warehouseid", "itemgroupid", "status"})
    public ResponseEntity<?> getItemStockReport(
            HttpServletRequest httpRequest,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="itemgroupid") String itemgroupid,
            @RequestParam(value="status") boolean status) throws IOException, JRException{
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        WarehouseEntity warehouse = warehouseService.getWarehouse(warehouseid);
        
        ItemGroupEntity itemgroup = itemgroupService.getItemGroup(itemgroupid);
        
        List<ItemWarehouseEntity> list = itemwarehouseService.
                getItemWarehouseList( warehouse, itemgroup, status);
        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("WAREHOUSE", warehouse.getCode());
        map.put("ITEMGROUP", itemgroup.getCode());
        if(status){
            map.put("STATUS", "ACTIVE");
        }
        else{
            map.put("STATUS", "INACTIVE");
        }

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/ItemStockReport.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(new ItemWarehouseTableModel(list)));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        responseHeaders.add("content-disposition", "attachment; filename=Partner.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
