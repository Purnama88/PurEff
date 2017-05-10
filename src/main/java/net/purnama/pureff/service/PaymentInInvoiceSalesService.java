/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
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
    
    @Transactional
    public void addPaymentinInvoiceSales(PaymentInInvoiceSalesEntity paymentininvoicesales) {
        paymentininvoicesalesDao.addPaymentInInvoiceSales(paymentininvoicesales);
    }
    
    @Transactional
    public void updatePaymentinInvoiceSales(PaymentInInvoiceSalesEntity paymentininvoicesales) {
        paymentininvoicesalesDao.updatePaymentInInvoiceSales(paymentininvoicesales);
    }
    
    @Transactional
    public void deletePaymentinInvoiceSales(String id) {
        paymentininvoicesalesDao.deletePaymentInInvoiceSales(id);
    }
    
    @Transactional
    public PaymentInInvoiceSalesEntity getPaymentinInvoiceSales(String id) {
        return paymentininvoicesalesDao.getPaymentInInvoiceSales(id);
    }
    
    @Transactional
    public PaymentInInvoiceSalesEntity
     getPaymentInInvoiceSalesEntity(PaymentInEntity paymentin,
                 InvoiceSalesEntity invoicesales){
         return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntity(paymentin, invoicesales);
    }
    
    @Transactional
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(PaymentInEntity paymentin){
        return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntityList(paymentin);
    }
    
    @Transactional
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(InvoiceSalesEntity invoicesales){
        return paymentininvoicesalesDao.
                 getPaymentInInvoiceSalesEntityList(invoicesales);
    }
}
