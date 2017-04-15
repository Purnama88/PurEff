/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutExpensesDraftDao;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutexpensesdraftService")
public class PaymentOutExpensesDraftService {
    
    @Autowired
    PaymentOutExpensesDraftDao paymentoutexpensesdraftDao;
    
    public PaymentOutExpensesDraftEntity
     getPaymentOutExpensesDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ExpensesEntity expenses){
         return paymentoutexpensesdraftDao.
                 getPaymentOutExpensesDraftEntity(paymentoutdraft, expenses);
    }
    
    public List<PaymentOutExpensesDraftEntity>
         getPaymentOutExpensesDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutexpensesdraftDao.
                 getPaymentOutExpensesDraftEntityList(paymentoutdraft);
    }
}
