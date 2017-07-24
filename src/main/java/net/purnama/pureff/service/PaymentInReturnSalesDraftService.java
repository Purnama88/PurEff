/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentInReturnSalesDraftDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentinreturnsalesdraftService")
public class PaymentInReturnSalesDraftService {
    
    @Autowired
    PaymentInReturnSalesDraftDao paymentinreturnsalesdraftDao;
    
    @Transactional
    public void addPaymentInReturnSalesDraft(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft) {
        paymentinreturnsalesdraftDao.addPaymentInReturnSalesDraft(paymentinreturnsalesdraft);
    }
    
    @Transactional
    public void updatePaymentInReturnSalesDraft(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft) {
        paymentinreturnsalesdraftDao.updatePaymentInReturnSalesDraft(paymentinreturnsalesdraft);
    }
    
    @Transactional
    public void deletePaymentInReturnSalesDraft(String id) {
        paymentinreturnsalesdraftDao.deletePaymentInReturnSalesDraft(id);
    }
    
    @Transactional
    public PaymentInReturnSalesDraftEntity getPaymentInReturnSalesDraft(String id) {
        return paymentinreturnsalesdraftDao.getPaymentInReturnSalesDraft(id);
    }
    
    @Transactional
    public PaymentInReturnSalesDraftEntity
     getPaymentInReturnSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 ReturnSalesEntity returnsales){
         return paymentinreturnsalesdraftDao.
                 getPaymentInReturnSalesDraftEntity(paymentindraft, returnsales);
    }
    
    @Transactional
    public List<PaymentInReturnSalesDraftEntity>
         getPaymentInReturnSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        return paymentinreturnsalesdraftDao.
                 getPaymentInReturnSalesDraftEntityList(paymentindraft);
    }
         
    @Transactional
    public List<PaymentInReturnSalesDraftEntity>
         getPaymentInReturnSalesDraftEntityList(PaymentInDraftEntity paymentindraft,
                 PartnerEntity partner, CurrencyEntity currency){
        return paymentinreturnsalesdraftDao.
                 getPaymentInReturnSalesDraftEntityList(paymentindraft, partner, currency);
    }
}
