/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.service.ReturnPurchaseService;
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
public class ReturnPurchaseController {
    
    @Autowired
    ReturnPurchaseService returnpurchaseService;
    
    @RequestMapping(value = "api/getReturnPurchaseList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ReturnPurchaseEntity> getReturnPurchaseList() {
        
        List<ReturnPurchaseEntity> ls = returnpurchaseService.getReturnPurchaseList();
        return ls;
    }
    
    @RequestMapping(value = "api/getReturnPurchase/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ReturnPurchaseEntity getReturnPurchase(@PathVariable String id) {
        return returnpurchaseService.getReturnPurchase(id);
    }

    @RequestMapping(value = "api/addReturnPurchase", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ReturnPurchaseEntity addReturnPurchase(@RequestBody ReturnPurchaseEntity returnpurchase) {
        returnpurchase.setId(IdGenerator.generateId());
        returnpurchase.setLastmodified(Calendar.getInstance());
        
        returnpurchaseService.addReturnPurchase(returnpurchase);
        
        return returnpurchase;
    }

    @RequestMapping(value = "api/updateReturnPurchase", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateReturnPurchase(@RequestBody ReturnPurchaseEntity returnpurchase) {
        returnpurchaseService.updateReturnPurchase(returnpurchase);
    }
}

