/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeInDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentTypeInDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public void addPaymentTypeInDraft(PaymentTypeInDraftEntity paymenttypeindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymenttypeindraft);
    }
    
    @Transactional
    public void updatePaymentTypeInDraft(PaymentTypeInDraftEntity paymenttypeindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymenttypeindraft);
    }
    
    @Transactional
    public void deletePaymentTypeInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeInDraftEntity p = getPaymentTypeInDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    @Transactional
    public PaymentTypeInDraftEntity getPaymentTypeInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeInDraftEntity p = (PaymentTypeInDraftEntity) 
                session.get(PaymentTypeInDraftEntity.class, id);
        return p;
    }
    
    @Transactional
    public List getPaymentTypeInDraftList(PaymentInDraftEntity paymentindraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.addOrder(Order.asc("date"));
        return c.list();
    }
    
}
