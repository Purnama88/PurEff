/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
import net.purnama.pureff.service.ReturnSalesService;
import net.purnama.pureff.service.PaymentInReturnSalesService;
import net.purnama.pureff.service.PaymentInService;
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
public class PaymentInReturnSalesController {
    
    @Autowired
    PaymentInReturnSalesService paymentinreturnsalesService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @Autowired
    ReturnSalesService returnsalesService;
    
    @RequestMapping(value = "api/getPaymentInReturnSalesEntityListByPaymentIn", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInReturnSalesEntity> getPaymentInReturnSalesEntityListByPaymentIn(@PathVariable String id) {
        
        List<PaymentInReturnSalesEntity> ls = paymentinreturnsalesService.
                getPaymentInReturnSalesEntityList(paymentinService.getPaymentIn(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesEntityListByReturnSales", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInReturnSalesEntity> getPaymentInReturnSalesEntityListByReturnSales(@PathVariable String id) {
        
        List<PaymentInReturnSalesEntity> ls = paymentinreturnsalesService.
                getPaymentInReturnSalesEntityList(returnsalesService.getReturnSales(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInReturnSalesEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentInReturnSalesEntity 
        getPaymentInReturnSalesEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentInReturnSalesEntity piisde = paymentinreturnsalesService.
                getPaymentInReturnSalesEntity(paymentinService.getPaymentIn(paymentid),
                        returnsalesService.getReturnSales(invoiceid));
        
        return piisde;
    }
    
}
