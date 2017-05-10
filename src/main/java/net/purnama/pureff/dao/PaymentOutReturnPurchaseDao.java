/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutReturnPurchaseEntity;
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
public class PaymentOutReturnPurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutReturnPurchase(PaymentOutReturnPurchaseEntity paymentoutreturnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutreturnpurchase);
    }
    
    public void updatePaymentOutReturnPurchase(PaymentOutReturnPurchaseEntity paymentoutreturnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutreturnpurchase);
    }
    
    public void deletePaymentOutReturnPurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutReturnPurchaseEntity p = getPaymentOutReturnPurchase(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutReturnPurchaseEntity getPaymentOutReturnPurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutReturnPurchaseEntity p = (PaymentOutReturnPurchaseEntity) 
                session.get(PaymentOutReturnPurchaseEntity.class, id);
        return p;
    }
    
    public PaymentOutReturnPurchaseEntity
     getPaymentOutReturnPurchaseEntity(PaymentOutEntity paymentout,
                 ReturnPurchaseEntity returnpurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        c.add(Restrictions.eq("returnpurchase", returnpurchase));
        return (PaymentOutReturnPurchaseEntity)c.uniqueResult();
    }
         
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(PaymentOutEntity paymentout){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        return c.list();
    }
         
    public List<PaymentOutReturnPurchaseEntity>
         getPaymentOutReturnPurchaseEntityList(ReturnPurchaseEntity returnpurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseEntity.class);
        c.add(Restrictions.eq("returnpurchase", returnpurchase));
        return c.list();
    }
    
}
