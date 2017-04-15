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
public class ItemReturnSalesDraftController {
    @Autowired
    ItemReturnSalesDraftService itemreturnsalesdraftService;
    
    @Autowired
    ReturnSalesDraftService returnsalesdraftService;
    
    @RequestMapping(value = "api/getItemReturnSalesDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemReturnSalesDraftEntity> getItemReturnSalesDraftList(@PathVariable String id) {
        ReturnSalesDraftEntity ad = returnsalesdraftService.getReturnSalesDraft(id);
        
        return itemreturnsalesdraftService.getItemReturnSalesDraftList(ad);
    }
}
