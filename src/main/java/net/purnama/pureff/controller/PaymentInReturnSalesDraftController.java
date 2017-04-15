/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.PaymentInReturnSalesDraftService;
import net.purnama.pureff.service.ReturnSalesService;
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
public class PaymentInReturnSalesDraftController {
    
    @Autowired
    PaymentInReturnSalesDraftService paymentinreturnsalesdraftService;
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInReturnSalesDraftEntity> getPaymentInReturnSalesDraftEntityList(@PathVariable String id) {
        
        List<PaymentInReturnSalesDraftEntity> ls = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntityList(paymentindraftService.getPaymentInDraft(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesDraftEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentInReturnSalesDraftEntity 
        getPaymentInReturnSalesDraftEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentInReturnSalesDraftEntity piisde = paymentinreturnsalesdraftService.
                getPaymentInReturnSalesDraftEntity(paymentindraftService.getPaymentInDraft(paymentid),
                        returnsalesService.getReturnSales(invoiceid));
        
        return piisde;
    }
    
}
