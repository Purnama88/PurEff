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
import net.purnama.pureff.entity.transactional.PaymentInEntity;
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
    public List getPaymentTypeInList(PaymentInEntity paymentin){
        return paymenttypeinDao.getPaymentTypeInList(paymentin);
    }
    
    @Transactional
    public List getPaymentTypeInList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        return paymenttypeinDao.getPaymentTypeInList(type, accepted, valid, begin, end);
    }
    
    @Transactional
    public List getPendingPaymentTypeInList(int type){
        return paymenttypeinDao.getPendingPaymentTypeInList(type);
    }
}
