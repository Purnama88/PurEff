/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.service.ExpensesService;
import net.purnama.pureff.service.ItemExpensesService;
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
public class ItemExpensesController {
    @Autowired
    ItemExpensesService itemexpensesService;
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getItemExpensesList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemExpensesList(@RequestParam(value="id") String id) {
        ExpensesEntity ad = expensesService.getExpenses(id);
        
        return ResponseEntity.ok(itemexpensesService.getItemExpensesList(ad));
    }
}
