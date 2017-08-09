/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
import net.purnama.pureff.service.InvoiceWarehouseOutService;
import net.purnama.pureff.service.ItemInvoiceWarehouseOutService;
import net.purnama.pureff.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
}
