/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutInvoicePurchaseDraftDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
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
    
    @Transactional
    public void addPaymentOutInvoicePurchaseDraft(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft) {
        paymentoutinvoicepurchasedraftDao.addPaymentOutInvoicePurchaseDraft(paymentoutinvoicepurchasedraft);
    }
    
    @Transactional
    public void updatePaymentOutInvoicePurchaseDraft(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft) {
        paymentoutinvoicepurchasedraftDao.updatePaymentOutInvoicePurchaseDraft(paymentoutinvoicepurchasedraft);
    }
    
    @Transactional
    public void deletePaymentOutInvoicePurchaseDraft(String id) {
        paymentoutinvoicepurchasedraftDao.deletePaymentOutInvoicePurchaseDraft(id);
    }
    
    @Transactional
    public PaymentOutInvoicePurchaseDraftEntity getPaymentOutReturnSalesDraft(String id) {
        return paymentoutinvoicepurchasedraftDao.getPaymentOutInvoicePurchaseDraft(id);
    }
    
    @Transactional
    public PaymentOutInvoicePurchaseDraftEntity
     getPaymentOutInvoicePurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 InvoicePurchaseEntity invoicepurchase){
         return paymentoutinvoicepurchasedraftDao.
                 getPaymentOutInvoicePurchaseDraftEntity(paymentoutdraft, invoicepurchase);
    }
    
    @Transactional
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        return paymentoutinvoicepurchasedraftDao.
                 getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft);
    }
         
    @Transactional
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft, 
                PartnerEntity partner, CurrencyEntity currency){
        return paymentoutinvoicepurchasedraftDao.
                 getPaymentOutInvoicePurchaseDraftEntityList(paymentoutdraft, partner, currency);
    }
}
