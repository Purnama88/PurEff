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
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.service.ItemReturnPurchaseService;
import net.purnama.pureff.service.ReturnPurchaseService;
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
public class ItemReturnPurchaseController {
    @Autowired
    ItemReturnPurchaseService itemreturnpurchaseService;
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getItemReturnPurchaseList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemReturnPurchaseList(@RequestParam(value="id") String id) {
        ReturnPurchaseEntity ad = returnpurchaseService.getReturnPurchase(id);
        
        return ResponseEntity.ok(itemreturnpurchaseService.getItemReturnPurchaseList(ad));
    }
    
    @RequestMapping(value = {"api/getItemReturnPurchaseList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "partnerid",
                "currencyid", "status"})
    public ResponseEntity<?> getItemReturnPurchaseList(
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
        
        List<ItemReturnPurchaseEntity> ls = itemreturnpurchaseService.
                getItemReturnPurchaseList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, partner, currency, status);
        
        return ResponseEntity.ok(ls);
    }
}
