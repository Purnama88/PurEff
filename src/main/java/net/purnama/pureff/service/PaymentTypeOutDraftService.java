/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentTypeOutDraftDao;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeOutDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymenttypeoutdraftService")
public class PaymentTypeOutDraftService {
    @Autowired
    PaymentTypeOutDraftDao paymenttypeoutdraftDao;
    
    @Transactional
    public void addPaymentTypeOutDraft(PaymentTypeOutDraftEntity paymenttypeoutdraft) {
        paymenttypeoutdraftDao.addPaymentTypeOutDraft(paymenttypeoutdraft);
    }
    
    @Transactional
    public void updatePaymentTypeOutDraft(PaymentTypeOutDraftEntity paymenttypeoutdraft) {
        paymenttypeoutdraftDao.updatePaymentTypeOutDraft(paymenttypeoutdraft);
    }
    
    @Transactional
    public void deletePaymentTypeOutDraft(String id) {
        paymenttypeoutdraftDao.deletePaymentTypeOutDraft(id);
    }
    
    @Transactional
    public PaymentTypeOutDraftEntity getPaymentTypeOutDraft(String id) {
        return paymenttypeoutdraftDao.getPaymentTypeOutDraft(id);
    }
    
    @Transactional
    public List<PaymentTypeOutDraftEntity> getPaymentTypeOutDraftList(PaymentOutDraftEntity paymentoutdraft){
        return paymenttypeoutdraftDao.getPaymentTypeOutDraftList(paymentoutdraft);
    }
    
}
