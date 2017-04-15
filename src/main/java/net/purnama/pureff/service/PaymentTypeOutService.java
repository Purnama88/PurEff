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
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
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
    public List getPaymentTypeOutList(PaymentOutEntity paymentout){
        return paymenttypeoutDao.getPaymentTypeOutList(paymentout);
    }
    
    @Transactional
    public List getPaymentTypeOutList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        return paymenttypeoutDao.getPaymentTypeOutList(type, accepted, valid, begin, end);
    }
    
    @Transactional
    public List getPendingPaymentTypeOutList(int type){
        return paymenttypeoutDao.getPendingPaymentTypeOutList(type);
    }
}
