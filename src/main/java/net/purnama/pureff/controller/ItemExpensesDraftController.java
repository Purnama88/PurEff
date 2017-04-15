/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemExpensesDraftEntity;
import net.purnama.pureff.service.ExpensesDraftService;
import net.purnama.pureff.service.ItemExpensesDraftService;
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
public class ItemExpensesDraftController {
    @Autowired
    ItemExpensesDraftService itemexpensesdraftService;
    
    @Autowired
    ExpensesDraftService expensesdraftService;
    
    @RequestMapping(value = "api/getItemExpensesDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemExpensesDraftEntity> getItemExpensesDraftList(@PathVariable String id) {
        ExpensesDraftEntity ad = expensesdraftService.getExpensesDraft(id);
        
        return itemexpensesdraftService.getItemExpensesDraftList(ad);
    }
}