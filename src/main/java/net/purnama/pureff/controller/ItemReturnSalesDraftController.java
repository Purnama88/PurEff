/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnSalesDraftEntity;
import net.purnama.pureff.service.ReturnSalesDraftService;
import net.purnama.pureff.service.ItemReturnSalesDraftService;
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
public class ItemReturnSalesDraftController {
    @Autowired
    ItemReturnSalesDraftService itemreturnsalesdraftService;
    
    @Autowired
    ReturnSalesDraftService returnsalesdraftService;
    
    @RequestMapping(value = "api/getItemReturnSalesDraftList", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItemReturnSalesDraftList(@RequestParam(value="id") String id) {
        ReturnSalesDraftEntity ad = returnsalesdraftService.getReturnSalesDraft(id);
        
        return ResponseEntity.ok(itemreturnsalesdraftService.getItemReturnSalesDraftList(ad));
    }
    
    @RequestMapping(value = "api/saveItemReturnSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> saveItemReturnSalesDraftList(
            @RequestBody List<ItemReturnSalesDraftEntity> itemreturnsalesdraftlist) {
        
        for(ItemReturnSalesDraftEntity itemreturnsalesdraft : itemreturnsalesdraftlist){
            if(itemreturnsalesdraft.getItem() != null){
                if(itemreturnsalesdraft.getId() != null){
                    itemreturnsalesdraftService.updateItemReturnSalesDraft(itemreturnsalesdraft);
                }
                else{
                    itemreturnsalesdraft.setId(IdGenerator.generateId());
                    itemreturnsalesdraftService.addItemReturnSalesDraft(itemreturnsalesdraft);
                }
            }
        }
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "api/deleteItemReturnSalesDraftList", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> deleteItemReturnSalesDraftList(
            @RequestBody List<ItemReturnSalesDraftEntity> itemreturnsalesdraftlist){
        
        for(ItemReturnSalesDraftEntity itemreturnsalesdraft : itemreturnsalesdraftlist){
            if(itemreturnsalesdraft.getId() != null){
                itemreturnsalesdraftService.deleteItemReturnSalesDraft(itemreturnsalesdraft.getId());
            }
            else{
            }
        }
        
        return ResponseEntity.ok("");
    }
}