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
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
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
public class PaymentTypeInDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PaymentTypeInEntity> getPaymentTypeInList(PaymentInEntity paymentin){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public PaymentTypeInEntity getPaymentTypeIn(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PaymentTypeInEntity p = (PaymentTypeInEntity) session.get(PaymentTypeInEntity.class, id);
        return p;
    }
    
    public PaymentTypeInEntity addPaymentTypeIn(PaymentTypeInEntity paymenttypein) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(paymenttypein);
        return paymenttypein;
    }

    public void updatePaymentTypeIn(PaymentTypeInEntity paymenttypein) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(paymenttypein);
    }
    
    public List getPaymentTypeInList(int type, boolean accepted, boolean valid,
            Calendar begin, Calendar end){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInEntity.class);
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
    
    public List getPendingPaymentTypeInList(int type){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInEntity.class);
        if(type < 4){
            c.add(Restrictions.eq("type", type));
        }
        c.add(Restrictions.eq("valid", true));
        c.add(Restrictions.eq("status", false));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<PaymentTypeInEntity>
         getPaymentTypeInList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, int type, boolean valid, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(PaymentTypeInEntity.class, "paymenttypein");
        c.createAlias("paymenttypein.paymentin", "paymentin");
        c.add(Restrictions.between("paymentin.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("paymentin.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("paymentin.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("paymentin.partner", partner));
        }
        if(type < 4){
            c.add(Restrictions.eq("type", type));
        }
        c.add(Restrictions.eq("paymenttypein.valid", valid));
        c.add(Restrictions.eq("paymenttypein.status", status));
        c.addOrder(Order.asc("paymentin.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<PaymentTypeInEntity>
         getPaymentTypeInList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(PaymentTypeInEntity.class, "paymenttypein");
        c.createAlias("paymenttypein.paymentin", "paymentin");
        c.add(Restrictions.between("paymentin.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("paymentin.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("paymentin.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("paymentin.partner", partner));
        }
        c.add(Restrictions.eq("paymentin.status", status));
        c.addOrder(Order.asc("paymentin.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
