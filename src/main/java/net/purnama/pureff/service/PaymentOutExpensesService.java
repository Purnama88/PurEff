/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutExpensesDao;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
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
    
    @Transactional
    public void addPaymentOutExpenses(PaymentOutExpensesEntity paymentoutexpenses) {
        paymentoutexpensesDao.addPaymentOutExpenses(paymentoutexpenses);
    }
    
    @Transactional
    public void updatePaymentOutExpenses(PaymentOutExpensesEntity paymentoutexpenses) {
        paymentoutexpensesDao.updatePaymentOutExpenses(paymentoutexpenses);
    }
    
    @Transactional
    public void deletePaymentOutExpenses(String id) {
        paymentoutexpensesDao.deletePaymentOutExpenses(id);
    }
    
    @Transactional
    public PaymentOutExpensesEntity getPaymentOutReturnSales(String id) {
        return paymentoutexpensesDao.getPaymentOutExpenses(id);
    }
    
    @Transactional
    public PaymentOutExpensesEntity
     getPaymentOutExpensesEntity(PaymentOutEntity paymentout,
                 ExpensesEntity expenses){
         return paymentoutexpensesDao.
                 getPaymentOutExpensesEntity(paymentout, expenses);
    }
    
    @Transactional
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(PaymentOutEntity paymentout){
        return paymentoutexpensesDao.
                 getPaymentOutExpensesEntityList(paymentout);
    }
    
    @Transactional
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(ExpensesEntity expenses){
        return paymentoutexpensesDao.
                 getPaymentOutExpensesEntityList(expenses);
    }
}
