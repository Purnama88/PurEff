/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentInReturnSalesDraftDao;
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
    
    public PaymentInReturnSalesDraftEntity
     getPaymentInReturnSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 ReturnSalesEntity returnsales){
         return paymentinreturnsalesdraftDao.
                 getPaymentInReturnSalesDraftEntity(paymentindraft, returnsales);
    }
    
    public List<PaymentInReturnSalesDraftEntity>
         getPaymentInReturnSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        return paymentinreturnsalesdraftDao.
                 getPaymentInReturnSalesDraftEntityList(paymentindraft);
    }
}
