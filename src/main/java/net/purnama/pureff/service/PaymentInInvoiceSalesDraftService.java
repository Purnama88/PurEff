/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentInInvoiceSalesDraftDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
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
    
    @Transactional
    public void addPaymentinInvoiceSalesDraft(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft) {
        paymentininvoicesalesdraftDao.addPaymentInInvoiceSalesDraft(paymentininvoicesalesdraft);
    }
    
    @Transactional
    public void updatePaymentinInvoiceSalesDraft(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft) {
        paymentininvoicesalesdraftDao.updatePaymentInInvoiceSalesDraft(paymentininvoicesalesdraft);
    }
    
    @Transactional
    public void deletePaymentinInvoiceSalesDraft(String id) {
        paymentininvoicesalesdraftDao.deletePaymentInInvoiceSalesDraft(id);
    }
    
    @Transactional
    public PaymentInInvoiceSalesDraftEntity getPaymentinInvoiceSalesDraft(String id) {
        return paymentininvoicesalesdraftDao.getPaymentInInvoiceSalesDraft(id);
    }
    
    @Transactional
    public PaymentInInvoiceSalesDraftEntity
     getPaymentInInvoiceSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 InvoiceSalesEntity invoicesales){
         return paymentininvoicesalesdraftDao.
                 getPaymentInInvoiceSalesDraftEntity(paymentindraft, invoicesales);
    }
    
    @Transactional
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        return paymentininvoicesalesdraftDao.
                 getPaymentInInvoiceSalesDraftEntityList(paymentindraft);
    }
     
    @Transactional
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft,
                 PartnerEntity partner, CurrencyEntity currency){
        return paymentininvoicesalesdraftDao.
                 getPaymentInInvoiceSalesDraftEntityList(paymentindraft, partner, currency);
    }
}
