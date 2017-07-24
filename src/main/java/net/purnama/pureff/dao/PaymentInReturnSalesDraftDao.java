/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInReturnSalesDraftEntity;
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
public class PaymentInReturnSalesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addPaymentInReturnSalesDraft(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentinreturnsalesdraft);
    }
    
    public void updatePaymentInReturnSalesDraft(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentinreturnsalesdraft);
    }
    
    public void deletePaymentInReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInReturnSalesDraftEntity p = getPaymentInReturnSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public PaymentInReturnSalesDraftEntity getPaymentInReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInReturnSalesDraftEntity p = (PaymentInReturnSalesDraftEntity) 
                session.get(PaymentInReturnSalesDraftEntity.class, id);
        return p;
    }
    
    public PaymentInReturnSalesDraftEntity
     getPaymentInReturnSalesDraftEntity(PaymentInDraftEntity paymentindraft,
                 ReturnSalesEntity returnsales){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.add(Restrictions.eq("returnsales", returnsales));
        return (PaymentInReturnSalesDraftEntity)c.uniqueResult();
    }
         
    public List<PaymentInReturnSalesDraftEntity>
         getPaymentInReturnSalesDraftEntityList(PaymentInDraftEntity paymentindraft){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesDraftEntity.class);
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
         
    public List<PaymentInReturnSalesDraftEntity>
         getPaymentInReturnSalesDraftEntityList(PaymentInDraftEntity paymentindraft,
                 PartnerEntity partner,
                 CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInReturnSalesDraftEntity.class, "paymentinreturnsalesdraft");
        c.add(Restrictions.eq("paymentindraft", paymentindraft));
        c.createAlias("paymentinreturnsalesdraft.returnsales", "returnsales");
        c.add(Restrictions.eq("returnsales.partner", partner));
        c.add(Restrictions.eq("returnsales.currency", currency));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
