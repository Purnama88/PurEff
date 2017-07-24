/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentInDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentInDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentInDraftEntity> getPaymentInDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInDraftEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public PaymentInDraftEntity getPaymentInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInDraftEntity p = (PaymentInDraftEntity) session.get(PaymentInDraftEntity.class, id);
        return p;
    }
    
    public PaymentInDraftEntity addPaymentInDraft(PaymentInDraftEntity paymentindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentindraft);
        return paymentindraft;
    }

    public void updatePaymentInDraft(PaymentInDraftEntity paymentindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentindraft);
    }
    
    public void deletePaymentInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentInDraftEntity p = getPaymentInDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getPaymentInDraftList(int itemperpage, int page, String sort,  String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        
        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countPaymentInDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentInDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
