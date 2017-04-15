/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentInInvoiceSalesDao;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentininvoicesalesService")
public class PaymentInInvoiceSalesService {
    
    @Autowired
    PaymentInInvoiceSalesDao paymentininvoicesalesDao;
    
    public PaymentInInvoiceSalesEntity
     getPaymentInInvoiceSalesEntity(PaymentInEntity paymentin,
                 InvoiceSalesEntity invoicesales){
         return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntity(paymentin, invoicesales);
    }
    
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(PaymentInEntity paymentin){
        return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntityList(paymentin);
    }
    
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(InvoiceSalesEntity invoicesales){
        return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntityList(invoicesales);
    }
}
