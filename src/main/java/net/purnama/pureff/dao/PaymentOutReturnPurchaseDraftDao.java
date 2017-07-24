/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
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
public class PaymentOutReturnPurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentOutReturnPurchaseDraft(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutreturnpurchasedraft);
    }
    
    public void updatePaymentOutReturnPurchaseDraft(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutreturnpurchasedraft);
    }
    
    public void deletePaymentOutReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutReturnPurchaseDraftEntity p = getPaymentOutReturnPurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentOutReturnPurchaseDraftEntity getPaymentOutReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutReturnPurchaseDraftEntity p = (PaymentOutReturnPurchaseDraftEntity) 
                session.get(PaymentOutReturnPurchaseDraftEntity.class, id);
        return p;
    }
    
    public PaymentOutReturnPurchaseDraftEntity
     getPaymentOutReturnPurchaseDraftEntity(PaymentOutDraftEntity paymentoutdraft,
                 ReturnPurchaseEntity returnpurchase){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.add(Restrictions.eq("returnpurchase", returnpurchase));
        return (PaymentOutReturnPurchaseDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentOutReturnPurchaseDraftEntity>
         getPaymentOutReturnPurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseDraftEntity.class);
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
         
    public List<PaymentOutReturnPurchaseDraftEntity>
         getPaymentOutReturnPurchaseDraftEntityList(PaymentOutDraftEntity paymentoutdraft,
                 PartnerEntity partner, CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutReturnPurchaseDraftEntity.class, 
                "paymentoutreturnpurchasedraft");
        c.add(Restrictions.eq("paymentoutdraft", paymentoutdraft));
        c.createAlias("paymentoutreturnpurchasedraft.returnpurchase", "returnpurchase");
        c.add(Restrictions.eq("returnpurchase.partner", partner));
        c.add(Restrictions.eq("returnpurchase.currency", currency));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
