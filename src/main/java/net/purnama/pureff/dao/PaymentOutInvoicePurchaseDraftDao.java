/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
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
public class PaymentOutInvoicePurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutInvoicePurchaseDraft(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutinvoicepurchasedraft);
    }
    
    public void updatePaymentOutInvoicePurchaseDraft(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutinvoicepurchasedraft);
    }
    
    public void deletePaymentOutInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutInvoicePurchaseDraftEntity p = getPaymentOutInvoicePurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutInvoicePurchaseDraftEntity getPaymentOutInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutInvoicePurchaseDraftEntity p = (PaymentOutInvoicePurchaseDraftEntity) 
                session.get(PaymentOutInvoicePurchaseDraftEntity.class, id);
        return p;
    }
    
    public PaymentOutInvoicePurchaseDraftEntity
     getPaymentOutInvoicePurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 InvoicePurchaseEntity invoicepurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.add(Restrictions.eq("invoicepurchase", invoicepurchase));
        return (PaymentOutInvoicePurchaseDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<PaymentOutInvoicePurchaseDraftEntity>
         getPaymentOutInvoicePurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft,
                 PartnerEntity partner, CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutInvoicePurchaseDraftEntity.class,
                "paymentoutinvoicepurchasedraft");
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.createAlias("paymentoutinvoicepurchasedraft.invoicepurchase", "invoicepurchase");
        c.add(Restrictions.eq("invoicepurchase.partner", partner));
        c.add(Restrictions.eq("invoicepurchase.currency", currency));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
