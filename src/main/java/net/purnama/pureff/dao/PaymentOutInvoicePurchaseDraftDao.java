/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
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
public class PaymentOutInvoicePurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public PaymentOutInvoicePurchaseDraftEntity
     getPaymentOutInvoicePurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 InvoicePurchaseEntity invoicepurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.add(Restrictions.eq("invoicepurchase", invoicepurchase));
        return (PaymentOutInvoicePurchaseDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        return c.list();
    }
    
}
