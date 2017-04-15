/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentInReturnSalesDao;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
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
    
    public PaymentInReturnSalesEntity
     getPaymentInReturnSalesEntity(PaymentInEntity paymentin,
                 ReturnSalesEntity returnsales){
         return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntity(paymentin, returnsales);
    }
    
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(PaymentInEntity paymentin){
        return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntityList(paymentin);
    }
         
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(ReturnSalesEntity returnsales){
        return paymentinreturnsalesDao.
                 getPaymentInReturnSalesEntityList(returnsales);
    }
}
