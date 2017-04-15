/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentOutEntity> getPaymentOutList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentOutEntity> ls = session.createQuery("from PaymentOutEntity").list();
        return ls;
    }
    
    public PaymentOutEntity getPaymentOut(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutEntity p = (PaymentOutEntity) session.get(PaymentOutEntity.class, id);
        return p;
    }
    
    public PaymentOutEntity addPaymentOut(PaymentOutEntity paymentout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentout);
        return paymentout;
    }

    public void updatePaymentOut(PaymentOutEntity paymentout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentout);
    }
}

