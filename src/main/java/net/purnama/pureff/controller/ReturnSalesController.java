/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.service.ReturnSalesService;
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
public class ReturnSalesController {
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @RequestMapping(value = "api/getReturnSalesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ReturnSalesEntity> getReturnSalesList() {
        
        List<ReturnSalesEntity> ls = returnsalesService.getReturnSalesList();
        return ls;
    }
    
    @RequestMapping(value = "api/getReturnSales/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ReturnSalesEntity getReturnSales(@PathVariable String id) {
        return returnsalesService.getReturnSales(id);
    }

    @RequestMapping(value = "api/addReturnSales", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ReturnSalesEntity addReturnSales(@RequestBody ReturnSalesEntity returnsales) {
        returnsales.setId(IdGenerator.generateId());
        returnsales.setLastmodified(Calendar.getInstance());
        
        returnsalesService.addReturnSales(returnsales);
        
        return returnsales;
    }

    @RequestMapping(value = "api/updateReturnSales", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateReturnSales(@RequestBody ReturnSalesEntity returnsales) {
        returnsalesService.updateReturnSales(returnsales);
    }
}
