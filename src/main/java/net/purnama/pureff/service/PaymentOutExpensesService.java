/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutExpensesDao;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutexpensesService")
public class PaymentOutExpensesService {
    
    @Autowired
    PaymentOutExpensesDao paymentoutexpensesDao;
    
    public PaymentOutExpensesEntity
     getPaymentOutExpensesEntity(PaymentOutEntity paymentout,
                 ExpensesEntity expenses){
         return paymentoutexpensesDao.
                 getPaymentOutExpensesEntity(paymentout, expenses);
    }
    
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(PaymentOutEntity paymentout){
        return paymentoutexpensesDao.
                 getPaymentOutExpensesEntityList(paymentout);
    }
    
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(ExpensesEntity expenses){
        return paymentoutexpensesDao.
                 getPaymentOutExpensesEntityList(expenses);
    }
}
