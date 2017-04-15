/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutDraftDao;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutdraftService")
public class PaymentOutDraftService {
    
    @Autowired
    PaymentOutDraftDao paymentoutdraftDao;

    @Transactional
    public List<PaymentOutDraftEntity> getPaymentOutDraftList() {
            return paymentoutdraftDao.getPaymentOutDraftList();
    }

    @Transactional
    public PaymentOutDraftEntity getPaymentOutDraft(String id) {
            return paymentoutdraftDao.getPaymentOutDraft(id);
    }

    @Transactional
    public void addPaymentOutDraft(PaymentOutDraftEntity paymentoutdraft) {
            paymentoutdraftDao.addPaymentOutDraft(paymentoutdraft);
    }

    @Transactional
    public void updatePaymentOutDraft(PaymentOutDraftEntity paymentoutdraft) {
            paymentoutdraftDao.updatePaymentOutDraft(paymentoutdraft);
    }

    @Transactional
    public void deletePaymentOutDraft(String id) {
            paymentoutdraftDao.deletePaymentOutDraft(id);
    }
}
