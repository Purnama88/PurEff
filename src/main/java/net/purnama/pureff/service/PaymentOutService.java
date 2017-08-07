/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentOutDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymentoutService")
public class PaymentOutService {
    
    @Autowired
    PaymentOutDao paymentoutDao;

    @Transactional
    public List<PaymentOutEntity> getPaymentOutList() {
            return paymentoutDao.getPaymentOutList();
    }

    @Transactional
    public PaymentOutEntity getPaymentOut(String id) {
            return paymentoutDao.getPaymentOut(id);
    }

    @Transactional
    public void addPaymentOut(PaymentOutEntity paymentout) {
            paymentoutDao.addPaymentOut(paymentout);
    }

    @Transactional
    public void updatePaymentOut(PaymentOutEntity paymentout) {
            paymentoutDao.updatePaymentOut(paymentout);
    }
    
    @Transactional
    public List getPaymentOutList(int itemperpage, int page, String sort, String keyword){
        return paymentoutDao.getPaymentOutList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countPaymentOutList(String keyword){
        return paymentoutDao.countPaymentOutList(keyword);
    }
    
    @Transactional
    public List getPaymentOutList(Calendar begin, Calendar end,
            WarehouseEntity warehouse, PartnerEntity partner, CurrencyEntity currency, 
            boolean status){
        return paymentoutDao.getPaymentOutList(begin, end, warehouse, partner, currency, status);
    }
}