/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import net.purnama.pureff.dao.PaymentOutInvoicePurchaseDraftDao;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutinvoicepurchasedraftService")
public class PaymentOutInvoicePurchaseDraftService {
    
    @Autowired
    PaymentOutInvoicePurchaseDraftDao paymentoutinvoicepurchasedraftDao;
    
    public PaymentOutInvoicePurchaseDraftEntity
     getPaymentOutInvoicePurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 InvoicePurchaseEntity invoicepurchase){
         return paymentoutinvoicepurchasedraftDao.
                 getPaymentOutInvoicePurchaseDraftEntity(paymentoutdraft, invoicepurchase);
    }
    
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutinvoicepurchasedraftDao.
                 getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft);
    }
}
