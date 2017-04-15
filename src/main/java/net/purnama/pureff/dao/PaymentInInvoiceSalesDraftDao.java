/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentInInvoiceSalesDraftDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    public PaymentInInvoiceSalesDraftEntity
     getPaymentInInvoiceSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 InvoiceSalesEntity invoicesales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.add(Restrictions.eq("invoicesales", invoicesales));
        return (PaymentInInvoiceSalesDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        return c.list();
    }
    
}
