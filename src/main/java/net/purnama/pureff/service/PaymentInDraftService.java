/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentInDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentindraftService")
public class PaymentInDraftService {
    
    @Autowired
    PaymentInDraftDao paymentindraftDao;

    @Transactional
    public List<PaymentInDraftEntity> getPaymentInDraftList() {
            return paymentindraftDao.getPaymentInDraftList();
    }

    @Transactional
    public PaymentInDraftEntity getPaymentInDraft(String id) {
            return paymentindraftDao.getPaymentInDraft(id);
    }

    @Transactional
    public void addPaymentInDraft(PaymentInDraftEntity paymentindraft) {
            paymentindraftDao.addPaymentInDraft(paymentindraft);
    }

    @Transactional
    public void updatePaymentInDraft(PaymentInDraftEntity paymentindraft) {
            paymentindraftDao.updatePaymentInDraft(paymentindraft);
    }

    @Transactional
    public void deletePaymentInDraft(String id) {
            paymentindraftDao.deletePaymentInDraft(id);
    }
    
    @Transactional
    public List getPaymentInDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        return paymentindraftDao.getPaymentInDraftList(itemperpage, page, sort, keyword, user);
    }
    
    @Transactional
    public int countPaymentInDraftList(String keyword, UserEntity user){
        return paymentindraftDao.countPaymentInDraftList(keyword, user);
    } 
}
