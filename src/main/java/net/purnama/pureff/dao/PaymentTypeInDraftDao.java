/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
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
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List getPaymentTypeInDraftList(PaymentInDraftEntity paymentindraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.addOrder(Order.asc("date"));
        return c.list();
    }
    
}
