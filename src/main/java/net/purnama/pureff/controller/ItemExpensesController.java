/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.ItemExpensesService;
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
public class ItemExpensesController {
    @Autowired
    ItemExpensesService itemexpensesService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getItemExpensesList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemExpensesEntity> getItemExpensesList(@PathVariable String id) {
        ExpensesEntity ad = expensesService.getExpenses(id);
        
        return itemexpensesService.getItemExpensesList(ad);
    }
}
