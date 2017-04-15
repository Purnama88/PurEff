/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class AdjustmentController {
    
    @Autowired
    AdjustmentService adjustmentService;
    
    @RequestMapping(value = "api/getAdjustmentList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<AdjustmentEntity> getAdjustmentList() {
        
        List<AdjustmentEntity> ls = adjustmentService.getAdjustmentList();
        return ls;
    }
    
    @RequestMapping(value = "api/getAdjustment/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public AdjustmentEntity getAdjustment(@PathVariable String id) {
        return adjustmentService.getAdjustment(id);
    }

    @RequestMapping(value = "api/addAdjustment", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public AdjustmentEntity addAdjustment(@RequestBody AdjustmentEntity adjustment) {
        adjustment.setId(IdGenerator.generateId());
        adjustment.setLastmodified(Calendar.getInstance());
        
        adjustmentService.addAdjustment(adjustment);
        
        return adjustment;
    }

    @RequestMapping(value = "api/updateAdjustment", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateAdjustment(@RequestBody AdjustmentEntity adjustment) {
        adjustmentService.updateAdjustment(adjustment);
    }
}
