/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInReturnSalesEntity;
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
public class PaymentInReturnSalesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public PaymentInReturnSalesEntity
     getPaymentInReturnSalesEntity(PaymentInEntity paymentin,
                 ReturnSalesEntity returnsales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        c.add(Restrictions.eq("returnsales", returnsales));
        return (PaymentInReturnSalesEntity)c.uniqueResult();
    }
         
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(PaymentInEntity paymentin){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        return c.list();
    }
         
    public List<PaymentInReturnSalesEntity>
         getPaymentInReturnSalesEntityList(ReturnSalesEntity returnsales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesEntity.class);
        c.add(Restrictions.eq("returnsales", returnsales));
        return c.list();
    }
    
}
