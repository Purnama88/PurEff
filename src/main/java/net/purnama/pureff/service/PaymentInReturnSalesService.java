/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentInReturnSalesDao;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentinreturnsalesService")
public class PaymentInReturnSalesService {
    
    @Autowired
    PaymentInReturnSalesDao paymentinreturnsalesDao;
    
    @Transactional
    public void addPaymentInReturnSales(PaymentInReturnSalesEntity paymentinreturnsales) {
        paymentinreturnsalesDao.addPaymentInReturnSales(paymentinreturnsales);
    }
    
    @Transactional
    public void updatePaymentInReturnSales(PaymentInReturnSalesEntity paymentinreturnsales) {
        paymentinreturnsalesDao.updatePaymentInReturnSales(paymentinreturnsales);
    }
    
    @Transactional
    public void deletePaymentInReturnSales(String id) {
        paymentinreturnsalesDao.deletePaymentInReturnSales(id);
    }
    
    @Transactional
    public PaymentInReturnSalesEntity getPaymentInReturnSales(String id) {
        return paymentinreturnsalesDao.getPaymentInReturnSales(id);
    }
    
    @Transactional
    public PaymentInReturnSalesEntity
     getPaymentInReturnSalesEntity(PaymentInEntity paymentin,
                 ReturnSalesEntity returnsales){
         return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntity(paymentin, returnsales);
    }
    
    @Transactional
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(PaymentInEntity paymentin){
        return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntityList(paymentin);
    }
    
    @Transactional
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(ReturnSalesEntity returnsales){
        return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntityList(returnsales);
    }
}
