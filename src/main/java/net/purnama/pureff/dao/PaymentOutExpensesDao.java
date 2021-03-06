/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentOutExpensesEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentOutExpensesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutExpenses(PaymentOutExpensesEntity paymentoutexpenses) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutexpenses);
    }
    
    public void updatePaymentOutExpenses(PaymentOutExpensesEntity paymentoutexpenses) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutexpenses);
    }
    
    public void deletePaymentOutExpenses(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutExpensesEntity p = getPaymentOutExpenses(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutExpensesEntity getPaymentOutExpenses(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutExpensesEntity p = (PaymentOutExpensesEntity) 
                session.get(PaymentOutExpensesEntity.class, id);
        return p;
    }
    
    public PaymentOutExpensesEntity
     getPaymentOutExpensesEntity(PaymentOutEntity paymentout,
                 ExpensesEntity expenses){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        c.add(Restrictions.eq("expenses", expenses));
        return (PaymentOutExpensesEntity)c.uniqueResult();
    }
         
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(PaymentOutEntity paymentout){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
         
    public List<PaymentOutExpensesEntity>
         getPaymentOutExpensesEntityList(ExpensesEntity expenses){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesEntity.class);
        c.add(Restrictions.eq("expenses", expenses));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    
}
