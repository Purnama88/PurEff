/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
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
    
    @Transactional
    public void addPaymentOutReturnPurchase(PaymentOutReturnPurchaseEntity paymentoutreturnpurchase) {
        paymentoutreturnpurchaseDao.addPaymentOutReturnPurchase(paymentoutreturnpurchase);
    }
    
    @Transactional
    public void updatePaymentOutReturnPurchase(PaymentOutReturnPurchaseEntity paymentoutreturnpurchase) {
        paymentoutreturnpurchaseDao.updatePaymentOutReturnPurchase(paymentoutreturnpurchase);
    }
    
    @Transactional
    public void deletePaymentOutReturnPurchase(String id) {
        paymentoutreturnpurchaseDao.deletePaymentOutReturnPurchase(id);
    }
    
    @Transactional
    public PaymentOutReturnPurchaseEntity getPaymentOutReturnSales(String id) {
        return paymentoutreturnpurchaseDao.getPaymentOutReturnPurchase(id);
    }
    
    @Transactional
    public PaymentOutReturnPurchaseEntity
     getPaymentOutReturnPurchaseEntity(PaymentOutEntity paymentout,
                 ReturnPurchaseEntity returnpurchase){
         return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntity(paymentout, returnpurchase);
    }
    
    @Transactional
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(PaymentOutEntity paymentout){
        return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntityList(paymentout);
    }
    
    @Transactional
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(ReturnPurchaseEntity returnpurchase){
        return paymentoutreturnpurchaseDao.
                 getPaymentOutReturnPurchaseEntityList(returnpurchase);
    }
}
