/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeOutEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PaymentTypeOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List getPaymentTypeOutList(PaymentOutEntity paymentout){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeOutEntity.class);
        c.add(Restrictions.eq("paymentout", paymentout));
        c.addOrder(Order.asc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public PaymentTypeOutEntity getPaymentTypeOut(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeOutEntity p = (PaymentTypeOutEntity) session.get(PaymentTypeOutEntity.class, id);
        return p;
    }
    
    public PaymentTypeOutEntity addPaymentTypeOut(PaymentTypeOutEntity paymenttypeout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymenttypeout);
        return paymenttypeout;
    }

    public void updatePaymentTypeOut(PaymentTypeOutEntity paymenttypeout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymenttypeout);
    }
    
    public List getPaymentTypeOutList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeOutEntity.class);
        if(type < 4){
            c.add(Restrictions.eq("type", type));
        }
        c.add(Restrictions.eq("status", accepted));
        c.add(Restrictions.eq("valid", valid));
        c.add(Restrictions.between("duedate", begin, end));
        c.addOrder(Order.asc("duedate"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List getPendingPaymentTypeOutList(int type){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeOutEntity.class);
        if(type < 4){
            c.add(Restrictions.eq("type", type));
        }
        c.add(Restrictions.eq("valid", true));
        c.add(Restrictions.eq("status", false));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<PaymentTypeOutEntity>
         getPaymentTypeOutList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, int type
//                 , boolean valid, boolean status
         ){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(PaymentTypeOutEntity.class, "paymenttypeout");
        c.createAlias("paymenttypeout.paymentout", "paymentin");
        c.add(Restrictions.between("paymentout.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("paymentout.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("paymentout.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("paymentout.partner", partner));
        }
        if(type < 4){
            c.add(Restrictions.eq("type", type));
        }
//        c.add(Restrictions.eq("paymentout.valid", valid));
//        c.add(Restrictions.eq("paymentout.status", status));
        c.addOrder(Order.asc("paymentout.date"));
        
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
