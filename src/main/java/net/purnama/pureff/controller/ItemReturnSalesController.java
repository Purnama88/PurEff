/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.service.ItemReturnSalesService;
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
public class ItemReturnSalesController {
    @Autowired
    ItemReturnSalesService itemreturnsalesService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @RequestMapping(value = "api/getItemReturnSalesList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemReturnSalesEntity> getItemReturnSalesList(@PathVariable String id) {
        ReturnSalesEntity ad = returnsalesService.getReturnSales(id);
        
        return itemreturnsalesService.getItemReturnSalesList(ad);
    }
}