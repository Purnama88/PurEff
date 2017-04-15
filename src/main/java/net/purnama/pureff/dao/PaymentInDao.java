/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentInDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentInEntity> getPaymentInList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentInEntity> ls = session.createQuery("from PaymentInEntity").list();
        return ls;
    }
    
    public PaymentInEntity getPaymentIn(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInEntity p = (PaymentInEntity) session.get(PaymentInEntity.class, id);
        return p;
    }
    
    public PaymentInEntity addPaymentIn(PaymentInEntity paymentin) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentin);
        return paymentin;
    }

    public void updatePaymentIn(PaymentInEntity paymentin) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentin);
    }
}
