/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutExpensesDraftEntity;
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
public class PaymentOutExpensesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
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
        return c.list();
    }
    
}
