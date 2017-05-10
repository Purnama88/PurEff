/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.service.ItemAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
