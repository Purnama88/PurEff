/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
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
public class PaymentOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentOutEntity> getPaymentOutList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public PaymentOutEntity getPaymentOut(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentOutEntity p = (PaymentOutEntity) session.get(PaymentOutEntity.class, id);
        return p;
    }
    
    public PaymentOutEntity addPaymentOut(PaymentOutEntity paymentout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymentout);
        return paymentout;
    }

    public void updatePaymentOut(PaymentOutEntity paymentout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymentout);
    }
    
    public List getPaymentOutList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        
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
    
    public int countPaymentOutList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentOutEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}

