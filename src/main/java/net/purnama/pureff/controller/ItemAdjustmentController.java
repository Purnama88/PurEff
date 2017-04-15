/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.service.ItemAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value = "api/getItemAdjustmentList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemAdjustmentEntity> getItemAdjustmentList(@PathVariable String id) {
        AdjustmentEntity ad = adjustmentService.getAdjustment(id);
        
        return itemadjustmentService.getItemAdjustmentList(ad);
    }
}
