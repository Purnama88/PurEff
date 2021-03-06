/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutExpensesDraftDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
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
    
    @Transactional
    public void addPaymentOutExpensesDraft(PaymentOutExpensesDraftEntity paymentoutexpensesdraft) {
        paymentoutexpensesdraftDao.addPaymentOutExpensesDraft(paymentoutexpensesdraft);
    }
    
    @Transactional
    public void updatePaymentOutExpensesDraft(PaymentOutExpensesDraftEntity paymentoutexpensesdraft) {
        paymentoutexpensesdraftDao.updatePaymentOutExpensesDraft(paymentoutexpensesdraft);
    }
    
    @Transactional
    public void deletePaymentOutExpensesDraft(String id) {
        paymentoutexpensesdraftDao.deletePaymentOutExpensesDraft(id);
    }
    
    @Transactional
    public PaymentOutExpensesDraftEntity getPaymentOutReturnSalesDraft(String id) {
        return paymentoutexpensesdraftDao.getPaymentOutExpensesDraft(id);
    }
    
    @Transactional
    public PaymentOutExpensesDraftEntity
     getPaymentOutExpensesDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ExpensesEntity expenses){
         return paymentoutexpensesdraftDao.
                 getPaymentOutExpensesDraftEntity(paymentoutdraft, expenses);
    }
    
    @Transactional
    public List<PaymentOutExpensesDraftEntity>
         getPaymentOutExpensesDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutexpensesdraftDao.
                 getPaymentOutExpensesDraftEntityList(paymentoutdraft);
    }
         
    @Transactional
    public List<PaymentOutExpensesDraftEntity>
         getPaymentOutExpensesDraftEntityList(PaymentOutDraftEntity paymentoutdraft,
                 PartnerEntity partner, CurrencyEntity currency){
        return paymentoutexpensesdraftDao.
                 getPaymentOutExpensesDraftEntityList(paymentoutdraft, partner, currency);
    }
}
