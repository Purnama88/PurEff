/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceSalesDraftEntity;
import net.purnama.pureff.service.InvoiceSalesDraftService;
import net.purnama.pureff.service.ItemInvoiceSalesDraftService;
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
public class ItemInvoiceSalesDraftController {
    @Autowired
    ItemInvoiceSalesDraftService iteminvoicesalesdraftService;
    
    @Autowired
    InvoiceSalesDraftService invoicesalesdraftService;
    
    @RequestMapping(value = "api/getItemInvoiceSalesDraftList/{id}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public List<ItemInvoiceSalesDraftEntity> getItemInvoiceSalesDraftList(@PathVariable String id) {
        InvoiceSalesDraftEntity ad = invoicesalesdraftService.getInvoiceSalesDraft(id);
        
        return iteminvoicesalesdraftService.getItemInvoiceSalesDraftList(ad);
    }
}