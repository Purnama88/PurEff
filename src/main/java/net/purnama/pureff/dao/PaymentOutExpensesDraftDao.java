/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
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
public class PaymentOutExpensesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutExpensesDraft(PaymentOutExpensesDraftEntity paymentoutexpensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutexpensesdraft);
    }
    
    public void updatePaymentOutExpensesDraft(PaymentOutExpensesDraftEntity paymentoutexpensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutexpensesdraft);
    }
    
    public void deletePaymentOutExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutExpensesDraftEntity p = getPaymentOutExpensesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutExpensesDraftEntity getPaymentOutExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutExpensesDraftEntity p = (PaymentOutExpensesDraftEntity) 
                session.get(PaymentOutExpensesDraftEntity.class, id);
        return p;
    }
    
    public PaymentOutExpensesDraftEntity
     getPaymentOutExpensesDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ExpensesEntity expenses){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.add(Restrictions.eq("expenses", expenses));
        return (PaymentOutExpensesDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentOutExpensesDraftEntity>
         getPaymentOutExpensesDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<PaymentOutExpensesDraftEntity>
         getPaymentOutExpensesDraftEntityList(PaymentOutDraftEntity paymentoutdraft, 
                 PartnerEntity partner, CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutExpensesDraftEntity.class, 
                "paymentoutexpensesdraft");
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.createAlias("paymentoutexpensesdraft.expenses", "expenses");
        c.add(Restrictions.eq("expenses.partner", partner));
        c.add(Restrictions.eq("expenses.currency", currency));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
