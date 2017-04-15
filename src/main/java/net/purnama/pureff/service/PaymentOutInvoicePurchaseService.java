/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutInvoicePurchaseDao;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutinvoicepurchaseService")
public class PaymentOutInvoicePurchaseService {
    
    @Autowired
    PaymentOutInvoicePurchaseDao paymentoutinvoicepurchaseDao;
    
    public PaymentOutInvoicePurchaseEntity
     getPaymentOutInvoicePurchaseEntity(PaymentOutEntity paymentout,
                 InvoicePurchaseEntity invoicepurchase){
         return paymentoutinvoicepurchaseDao.
                 getPaymentOutInvoicePurchaseEntity(paymentout, invoicepurchase);
    }
    
    public List<PaymentOutInvoicePurchaseEntity>
         getPaymentOutInvoicePurchaseEntityList(PaymentOutEntity paymentout){
        return paymentoutinvoicepurchaseDao.
                 getPaymentOutInvoicePurchaseEntityList(paymentout);
    }
         
    public List<PaymentOutInvoicePurchaseEntity>
         getPaymentOutInvoicePurchaseEntityList(InvoicePurchaseEntity invoicepurchase){
        return paymentoutinvoicepurchaseDao.
                 getPaymentOutInvoicePurchaseEntityList(invoicepurchase);
    }
    
}
