/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentInDao;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentinService")
public class PaymentInService {
    
    @Autowired
    PaymentInDao paymentinDao;

    @Transactional
    public List<PaymentInEntity> getPaymentInList() {
            return paymentinDao.getPaymentInList();
    }

    @Transactional
    public PaymentInEntity getPaymentIn(String id) {
            return paymentinDao.getPaymentIn(id);
    }

    @Transactional
    public void addPaymentIn(PaymentInEntity paymentin) {
            paymentinDao.addPaymentIn(paymentin);
    }

    @Transactional
    public void updatePaymentIn(PaymentInEntity paymentin) {
            paymentinDao.updatePaymentIn(paymentin);
    }
    
    @Transactional
    public List getPaymentInList(int itemperpage, int page, String sort, String keyword){
        return paymentinDao.getPaymentInList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countPaymentInList(String keyword){
        return paymentinDao.countPaymentInList(keyword);
    }
}

