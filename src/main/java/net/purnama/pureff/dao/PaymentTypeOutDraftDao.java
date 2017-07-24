/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentTypeOutDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentTypeOutDraftDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void addPaymentTypeOutDraft(PaymentTypeOutDraftEntity paymenttypeoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymenttypeoutdraft);
    }
    
    public void updatePaymentTypeOutDraft(PaymentTypeOutDraftEntity paymenttypeoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymenttypeoutdraft);
    }
    
    public void deletePaymentTypeOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeOutDraftEntity p = getPaymentTypeOutDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentTypeOutDraftEntity getPaymentTypeOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeOutDraftEntity p = (PaymentTypeOutDraftEntity) 
                session.get(PaymentTypeOutDraftEntity.class, id);
        return p;
    }
    
    public List getPaymentTypeOutDraftList(PaymentOutDraftEntity paymentoutdraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeOutDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.addOrder(Order.asc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
}
