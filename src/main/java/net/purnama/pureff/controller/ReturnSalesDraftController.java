/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.pureff.service.ReturnSalesDraftService;
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
public class ReturnSalesDraftController {
    
    @Autowired
    ReturnSalesDraftService returnsalesdraftService;
    
    @RequestMapping(value = "api/getReturnSalesDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<ReturnSalesDraftEntity> getReturnSalesDraftList() {
        
        List<ReturnSalesDraftEntity> ls = returnsalesdraftService.getReturnSalesDraftList();
        return ls;
    }
    
    @RequestMapping(value = "api/getReturnSalesDraft/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ReturnSalesDraftEntity getReturnSalesDraft(@PathVariable String id) {
        return returnsalesdraftService.getReturnSalesDraft(id);
    }

    @RequestMapping(value = "api/addReturnSalesDraft", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ReturnSalesDraftEntity addReturnSalesDraft(@RequestBody ReturnSalesDraftEntity returnsalesdraft) {
        returnsalesdraft.setId(IdGenerator.generateId());
        returnsalesdraft.setLastmodified(Calendar.getInstance());
        
        returnsalesdraftService.addReturnSalesDraft(returnsalesdraft);
        
        return returnsalesdraft;
    }

    @RequestMapping(value = "api/updateReturnSalesDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateReturnSalesDraft(@RequestBody ReturnSalesDraftEntity returnsalesdraft) {
        returnsalesdraftService.updateReturnSalesDraft(returnsalesdraft);
    }

    @RequestMapping(value = "api/deleteReturnSalesDraft/{id}", method = RequestMethod.DELETE, 
            headers = "Accept=application/json")
    public void deleteReturnSalesDraft(@PathVariable String id) {
        returnsalesdraftService.deleteReturnSalesDraft(id);		
    }
}
