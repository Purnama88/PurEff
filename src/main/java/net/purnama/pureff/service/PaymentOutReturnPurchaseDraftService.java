/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutReturnPurchaseDraftDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutreturnpurchasedraftService")
public class PaymentOutReturnPurchaseDraftService {
    
    @Autowired
    PaymentOutReturnPurchaseDraftDao paymentoutreturnpurchasedraftDao;
    
    @Transactional
    public void addPaymentOutReturnPurchaseDraft(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft) {
        paymentoutreturnpurchasedraftDao.addPaymentOutReturnPurchaseDraft(paymentoutreturnpurchasedraft);
    }
    
    @Transactional
    public void updatePaymentOutReturnPurchaseDraft(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft) {
        paymentoutreturnpurchasedraftDao.updatePaymentOutReturnPurchaseDraft(paymentoutreturnpurchasedraft);
    }
    
    @Transactional
    public void deletePaymentOutReturnPurchaseDraft(String id) {
        paymentoutreturnpurchasedraftDao.deletePaymentOutReturnPurchaseDraft(id);
    }
    
    @Transactional
    public PaymentOutReturnPurchaseDraftEntity getPaymentOutReturnSalesDraft(String id) {
        return paymentoutreturnpurchasedraftDao.getPaymentOutReturnPurchaseDraft(id);
    }
    
    @Transactional
    public PaymentOutReturnPurchaseDraftEntity
     getPaymentOutReturnPurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ReturnPurchaseEntity returnpurchase){
         return paymentoutreturnpurchasedraftDao.
                 getPaymentOutReturnPurchaseDraftEntity(paymentoutdraft, returnpurchase);
    }
    
    @Transactional
    public List<PaymentOutReturnPurchaseDraftEntity>
         getPaymentOutReturnPurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutreturnpurchasedraftDao.
                 getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
    }
         
    @Transactional
    public List<PaymentOutReturnPurchaseDraftEntity>
         getPaymentOutReturnPurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft,
                 PartnerEntity partner, CurrencyEntity currency){
        return paymentoutreturnpurchasedraftDao.
                 getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft, partner, currency);
    }
}
