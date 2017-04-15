/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutReturnPurchaseDraftDao;
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
    
    public PaymentOutReturnPurchaseDraftEntity
     getPaymentOutReturnPurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ReturnPurchaseEntity returnpurchase){
         return paymentoutreturnpurchasedraftDao.
                 getPaymentOutReturnPurchaseDraftEntity(paymentoutdraft, returnpurchase);
    }
    
    public List<PaymentOutReturnPurchaseDraftEntity>
         getPaymentOutReturnPurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutreturnpurchasedraftDao.
                 getPaymentOutReturnPurchaseDraftEntityList(paymentoutdraft);
    }
}
