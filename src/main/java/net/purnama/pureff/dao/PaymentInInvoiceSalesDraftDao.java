/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInInvoiceSalesDraftEntity;
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
public class PaymentInInvoiceSalesDraftDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentInInvoiceSalesDraft(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentininvoicesalesdraft);
    }
    
    public void updatePaymentInInvoiceSalesDraft(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentininvoicesalesdraft);
    }
    
    public void deletePaymentInInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInInvoiceSalesDraftEntity p = getPaymentInInvoiceSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentInInvoiceSalesDraftEntity getPaymentInInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInInvoiceSalesDraftEntity p = (PaymentInInvoiceSalesDraftEntity) 
                session.get(PaymentInInvoiceSalesDraftEntity.class, id);
        return p;
    }
    
    public PaymentInInvoiceSalesDraftEntity
     getPaymentInInvoiceSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 InvoiceSalesEntity invoicesales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.add(Restrictions.eq("invoicesales", invoicesales));
        return (PaymentInInvoiceSalesDraftEntity)c.uniqueResult();
    }
       
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInInvoiceSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
     
    public List<PaymentInInvoiceSalesDraftEntity>
         getPaymentInInvoiceSalesDraftEntityList(PaymentInDraftEntity paymentindraft,
                 PartnerEntity partner,
                 CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(PaymentInInvoiceSalesDraftEntity.class, "paymentininvoicesalesdraft");
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.createAlias("paymentininvoicesalesdraft.invoicesales", "invoicesales");
        c.add(Restrictions.eq("invoicesales.partner", partner));
        c.add(Restrictions.eq("invoicesales.currency", currency));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
}
