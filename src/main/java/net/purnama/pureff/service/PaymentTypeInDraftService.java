/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PaymentTypeInDraftDao;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("paymenttypeindraftService")
public class PaymentTypeInDraftService {
    
    @Autowired
    PaymentTypeInDraftDao paymenttypeindraftDao;
    
    @Transactional
    public List getPaymentTypeInDraftList(PaymentInDraftEntity paymentindraft){
        return paymenttypeindraftDao.getPaymentTypeInDraftList(paymentindraft);
    }
    
}
