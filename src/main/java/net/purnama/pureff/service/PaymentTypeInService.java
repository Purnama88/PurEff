/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentTypeInDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymenttypeinService")
public class PaymentTypeInService {
    @Autowired
    PaymentTypeInDao paymenttypeinDao;
    
    @Transactional
    public void addPaymentTypeIn(PaymentTypeInEntity paymenttypein){
        paymenttypeinDao.addPaymentTypeIn(paymenttypein);
    }
    
    @Transactional
    public void updatePaymentTypeIn(PaymentTypeInEntity paymenttypein){
        paymenttypeinDao.updatePaymentTypeIn(paymenttypein);
    }
    
    @Transactional
    public List<PaymentTypeInEntity> getPaymentTypeInList(PaymentInEntity paymentin){
        return paymenttypeinDao.getPaymentTypeInList(paymentin);
    }
    
    @Transactional
    public List<PaymentTypeInEntity> getPaymentTypeInList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        return paymenttypeinDao.getPaymentTypeInList(type, accepted, valid, begin, end);
    }
    
    @Transactional
    public List<PaymentTypeInEntity> getPendingPaymentTypeInList(int type){
        return paymenttypeinDao.getPendingPaymentTypeInList(type);
    }
    
    @Transactional
    public List<PaymentTypeInEntity> getPaymentTypeInList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, int type, boolean valid, boolean status){
        return paymenttypeinDao.getPaymentTypeInList(start, end, warehouse, partner, currency, type,
                valid, status
        );
    }
    
    @Transactional
    public List<PaymentTypeInEntity> getPaymentTypeInList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency){
        return paymenttypeinDao.getPaymentTypeInList(start, end, warehouse, partner, currency);
    }
}
