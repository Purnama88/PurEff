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
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value = "api/getItemExpensesDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemExpensesDraftList(@RequestParam(value="id") String id) {
        ExpensesDraftEntity ad = expensesdraftService.getExpensesDraft(id);
        
        return ResponseEntity.ok(itemexpensesdraftService.getItemExpensesDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemExpensesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemExpensesDraftList(
            @RequestBody List<ItemExpensesDraftEntity> itemexpensesdraftlist) {
        
        for(ItemExpensesDraftEntity itemexpensesdraft : itemexpensesdraftlist){
            if(!itemexpensesdraft.getDescription().isEmpty()){
                if(itemexpensesdraft.getId() != null){
                    itemexpensesdraftService.updateItemExpensesDraft(itemexpensesdraft);
                }
                else{
                    itemexpensesdraft.setId(IdGenerator.generateId());
                    itemexpensesdraftService.addItemExpensesDraft(itemexpensesdraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemExpensesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemExpensesDraftList(
            @RequestBody List<ItemExpensesDraftEntity> itemexpensesdraftlist){
        
        for(ItemExpensesDraftEntity itemexpensesdraft : itemexpensesdraftlist){
            if(itemexpensesdraft.getId() != null){
                itemexpensesdraftService.deleteItemExpensesDraft(itemexpensesdraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}