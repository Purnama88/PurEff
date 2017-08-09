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
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.service.ItemAdjustmentService;
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
public class ItemAdjustmentController {
    @Autowired
    ItemAdjustmentService itemadjustmentService;
    
    @Autowired
    AdjustmentService adjustmentService;
    
    @RequestMapping(value = "api/getItemAdjustmentList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemAdjustmentList(@RequestParam(value="id") String id){
        AdjustmentEntity ad = adjustmentService.getAdjustment(id);
        
        return ResponseEntity.ok(itemadjustmentService.getItemAdjustmentList(ad));
    }
    
    @RequestMapping(value = {"api/getItemAdjustmentList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"startdate", "enddate", 
                "warehouseid", "status"})
    public ResponseEntity<?> getItemAdjustmentList(
            HttpServletRequest httpRequest,
            @RequestParam(value="startdate")@DateTimeFormat(pattern="MMddyyyy") Calendar start,
            @RequestParam(value="enddate")@DateTimeFormat(pattern="MMddyyyy") Calendar end,
            @RequestParam(value="warehouseid") String warehouseid,
            @RequestParam(value="status") boolean status){
        
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(warehouseid);
        
        List<ItemAdjustmentEntity> ls = itemadjustmentService.
                getItemAdjustmentList(CalendarUtil.toStartOfDay(start), 
                        CalendarUtil.toEndOfDay(end), warehouse, status);
        
        return ResponseEntity.ok(ls);
    }
}
