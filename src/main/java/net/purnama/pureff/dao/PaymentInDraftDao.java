/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentInDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentInDraftEntity> getPaymentInDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentInDraftEntity> ls = session.createQuery("from PaymentInDraftEntity").list();
        return ls;
    }
    
    public PaymentInDraftEntity getPaymentInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInDraftEntity p = (PaymentInDraftEntity) session.get(PaymentInDraftEntity.class, id);
        return p;
    }
    
    public PaymentInDraftEntity addPaymentInDraft(PaymentInDraftEntity paymentindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentindraft);
        return paymentindraft;
    }

    public void updatePaymentInDraft(PaymentInDraftEntity paymentindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentindraft);
    }
    
    public void deletePaymentInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInDraftEntity p = getPaymentInDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
