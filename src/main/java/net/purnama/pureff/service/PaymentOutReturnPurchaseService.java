/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutReturnPurchaseDao;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutReturnPurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutreturnpurchaseService")
public class PaymentOutReturnPurchaseService {
    
    @Autowired
    PaymentOutReturnPurchaseDao paymentoutreturnpurchaseDao;
    
    public PaymentOutReturnPurchaseEntity
     getPaymentOutReturnPurchaseEntity(PaymentOutEntity paymentout,
                 ReturnPurchaseEntity returnpurchase){
         return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntity(paymentout, returnpurchase);
    }
    
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(PaymentOutEntity paymentout){
        return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntityList(paymentout);
    }
    
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(ReturnPurchaseEntity returnpurchase){
        return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntityList(returnpurchase);
    }
}
