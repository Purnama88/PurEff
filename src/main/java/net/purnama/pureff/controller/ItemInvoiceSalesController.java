/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.ItemInvoiceSalesService;
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
public class ItemInvoiceSalesController {
    @Autowired
    ItemInvoiceSalesService iteminvoicesalesService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getItemInvoiceSalesList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemInvoiceSalesList(@RequestParam(value="id") String id) {
        InvoiceSalesEntity ad = invoicesalesService.getInvoiceSales(id);
        
        return ResponseEntity.ok(iteminvoicesalesService.getItemInvoiceSalesList(ad));
    }
    
    @RequestMapping(value = {"api/getItemInvoiceSalesList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getItemInvoiceSalesList(
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
        
        List<ItemInvoiceSalesEntity> ls = iteminvoicesalesService.
                getItemInvoiceSalesList(start, end, warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
}
