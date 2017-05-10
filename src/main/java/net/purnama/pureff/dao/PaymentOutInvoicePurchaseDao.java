/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
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
public class PaymentOutInvoicePurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutInvoicePurchase(PaymentOutInvoicePurchaseEntity paymentoutinvoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutinvoicepurchase);
    }
    
    public void updatePaymentOutInvoicePurchase(PaymentOutInvoicePurchaseEntity paymentoutinvoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutinvoicepurchase);
    }
    
    public void deletePaymentOutInvoicePurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutInvoicePurchaseEntity p = getPaymentOutInvoicePurchase(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutInvoicePurchaseEntity getPaymentOutInvoicePurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutInvoicePurchaseEntity p = (PaymentOutInvoicePurchaseEntity) 
                session.get(PaymentOutInvoicePurchaseEntity.class, id);
        return p;
    }
    
    public PaymentOutInvoicePurchaseEntity
     getPaymentOutInvoicePurchaseEntity(PaymentOutEntity paymentout,
                 InvoicePurchaseEntity invoicepurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        c.add(Restrictions.eq("invoicepurchase", invoicepurchase));
        return (PaymentOutInvoicePurchaseEntity)c.uniqueResult();
    }
         
    public List<PaymentOutInvoicePurchaseEntity>
         getPaymentOutInvoicePurchaseEntityList(PaymentOutEntity paymentout){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        return c.list();
    }
         
    public List<PaymentOutInvoicePurchaseEntity>
         getPaymentOutInvoicePurchaseEntityList(InvoicePurchaseEntity invoicepurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseEntity.class);
        c.add(Restrictions.eq("invoicepurchase", invoicepurchase));
        return c.list();
    }
    
}
