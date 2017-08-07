/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentTypeOutDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymenttypeoutService")
public class PaymentTypeOutService {
    @Autowired
    PaymentTypeOutDao paymenttypeoutDao;
    
    
    @Transactional
    public void addPaymentTypeOut(PaymentTypeOutEntity paymenttypeout){
        paymenttypeoutDao.addPaymentTypeOut(paymenttypeout);
    }
    
    @Transactional
    public void updatePaymentTypeOut(PaymentTypeOutEntity paymenttypeout){
        paymenttypeoutDao.updatePaymentTypeOut(paymenttypeout);
    }
    
    @Transactional
    public List<PaymentTypeOutEntity> getPaymentTypeOutList(PaymentOutEntity paymentout){
        return paymenttypeoutDao.getPaymentTypeOutList(paymentout);
    }
    
    @Transactional
    public List<PaymentTypeOutEntity> getPaymentTypeOutList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        return paymenttypeoutDao.getPaymentTypeOutList(type, accepted, valid, begin, end);
    }
    
    @Transactional
    public List<PaymentTypeOutEntity> getPendingPaymentTypeOutList(int type){
        return paymenttypeoutDao.getPendingPaymentTypeOutList(type);
    }
    
    @Transactional
    public List<PaymentTypeOutEntity> getPaymentTypeOutList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, int type, boolean status){
        return paymenttypeoutDao.getPaymentTypeOutList(start, end, warehouse, partner, currency, type, status);
    }
}
