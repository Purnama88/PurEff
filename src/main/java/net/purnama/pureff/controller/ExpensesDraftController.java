/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import net.purnama.pureff.service.ExpensesDraftService;
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
public class ExpensesDraftController {
    
    @Autowired
    ExpensesDraftService expensesService;
    
    @RequestMapping(value = "api/getExpensesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ExpensesDraftEntity> getExpensesDraftList() {
        
        List<ExpensesDraftEntity> ls = expensesService.getExpensesDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getExpensesDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ExpensesDraftEntity getExpensesDraft(@PathVariable String id) {
        return expensesService.getExpensesDraft(id);
    }

    @RequestMapping(value = "api/addExpensesDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ExpensesDraftEntity addExpensesDraft(@RequestBody ExpensesDraftEntity expenses) {
        expenses.setId(IdGenerator.generateId());
        expenses.setLastmodified(Calendar.getInstance());
        
        expensesService.addExpensesDraft(expenses);
        
        return expenses;
    }

    @RequestMapping(value = "api/updateExpensesDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateExpensesDraft(@RequestBody ExpensesDraftEntity expenses) {
        expensesService.updateExpensesDraft(expenses);
    }

    @RequestMapping(value = "api/deleteExpensesDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteExpensesDraft(@PathVariable String id) {
        expensesService.deleteExpensesDraft(id);		
    }
}
