/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentInInvoiceSalesDraftDao;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentininvoicesalesdraftService")
public class PaymentInInvoiceSalesDraftService {
    
    @Autowired
    PaymentInInvoiceSalesDraftDao paymentininvoicesalesdraftDao;
    
    public PaymentInInvoiceSalesDraftEntity
     getPaymentInInvoiceSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 InvoiceSalesEntity invoicesales){
         return paymentininvoicesalesdraftDao.
                 getPaymentInInvoiceSalesDraftEntity(paymentindraft, invoicesales);
    }
    
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        return paymentininvoicesalesdraftDao.
                 getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
    }
}
