/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.PaymentOutDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class PaymentOutDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentOutDraftEntity> getPaymentOutDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentOutDraftEntity> ls = session.createQuery("from PaymentOutDraftEntity").list();
        return ls;
    }
    
    public PaymentOutDraftEntity getPaymentOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutDraftEntity p = (PaymentOutDraftEntity) session.get(PaymentOutDraftEntity.class, id);
        return p;
    }
    
    public PaymentOutDraftEntity addPaymentOutDraft(PaymentOutDraftEntity paymentoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentoutdraft);
        return paymentoutdraft;
    }

    public void updatePaymentOutDraft(PaymentOutDraftEntity paymentoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentoutdraft);
    }
    
    public void deletePaymentOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutDraftEntity p = getPaymentOutDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getPaymentOutDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        
        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countPaymentOutDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
