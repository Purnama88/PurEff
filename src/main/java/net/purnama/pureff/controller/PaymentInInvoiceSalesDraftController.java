/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.pureff.service.InvoiceSalesService;
import net.purnama.pureff.service.PaymentInDraftService;
import net.purnama.pureff.service.PaymentInInvoiceSalesDraftService;
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
public class PaymentInInvoiceSalesDraftController {
    
    @Autowired
    PaymentInInvoiceSalesDraftService paymentininvoicesalesdraftService;
    
    @Autowired
    PaymentInDraftService paymentindraftService;
    
    @Autowired
    InvoiceSalesService invoicesalesService;
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraftEntityList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public List<PaymentInInvoiceSalesDraftEntity> getPaymentInInvoiceSalesDraftEntityList(@PathVariable String id) {
        
        List<PaymentInInvoiceSalesDraftEntity> ls = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntityList(paymentindraftService.getPaymentInDraft(id));
        return ls;
    }
    
    @RequestMapping(value = "api/getPaymentInInvoiceSalesDraftEntity/{paymentid}/{invoiceid}",
            method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public PaymentInInvoiceSalesDraftEntity 
        getPaymentInInvoiceSalesDraftEntity(@PathVariable String paymentid, @PathVariable String invoiceid){
        PaymentInInvoiceSalesDraftEntity piisde = paymentininvoicesalesdraftService.
                getPaymentInInvoiceSalesDraftEntity(paymentindraftService.getPaymentInDraft(paymentid),
                        invoicesalesService.getInvoiceSales(invoiceid));
        
        return piisde;
    }
}
