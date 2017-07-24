/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentInInvoiceSalesEntity;
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
public class PaymentInInvoiceSalesDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentInInvoiceSales(PaymentInInvoiceSalesEntity paymentininvoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentininvoicesales);
    }
    
    public void updatePaymentInInvoiceSales(PaymentInInvoiceSalesEntity paymentininvoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentininvoicesales);
    }
    
    public void deletePaymentInInvoiceSales(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInInvoiceSalesEntity p = getPaymentInInvoiceSales(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentInInvoiceSalesEntity getPaymentInInvoiceSales(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInInvoiceSalesEntity p = (PaymentInInvoiceSalesEntity) 
                session.get(PaymentInInvoiceSalesEntity.class, id);
        return p;
    }
    
    public PaymentInInvoiceSalesEntity
     getPaymentInInvoiceSalesEntity(PaymentInEntity paymentin,
                 InvoiceSalesEntity invoicesales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        c.add(Restrictions.eq("invoicesales", invoicesales));
        return (PaymentInInvoiceSalesEntity)c.uniqueResult();
    }
         
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(PaymentInEntity paymentin){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
         
    public List<PaymentInInvoiceSalesEntity>
         getPaymentInInvoiceSalesEntityList(InvoiceSalesEntity invoicesales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesEntity.class);
        c.add(Restrictions.eq("invoicesales", invoicesales));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
