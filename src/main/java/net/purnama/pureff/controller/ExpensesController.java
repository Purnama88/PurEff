/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.service.ExpensesService;
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
public class ExpensesController {
    
    @Autowired
    ExpensesService expensesService;
    
    @RequestMapping(value = "api/getExpensesList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ExpensesEntity> getExpensesList() {
        
        List<ExpensesEntity> ls = expensesService.getExpensesList();
        return ls;
    }
    
    @RequestMapping(value = "api/getExpenses/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ExpensesEntity getExpenses(@PathVariable String id) {
        return expensesService.getExpenses(id);
    }

    @RequestMapping(value = "api/addExpenses", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ExpensesEntity addExpenses(@RequestBody ExpensesEntity expenses) {
        expenses.setId(IdGenerator.generateId());
        expenses.setLastmodified(Calendar.getInstance());
        
        expensesService.addExpenses(expenses);
        
        return expenses;
    }

    @RequestMapping(value = "api/updateExpenses", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateExpenses(@RequestBody ExpensesEntity expenses) {
        expensesService.updateExpenses(expenses);
    }
}
