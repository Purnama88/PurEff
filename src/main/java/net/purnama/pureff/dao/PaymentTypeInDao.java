/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.entity.transactional.PaymentTypeInEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    
    public List getPaymentTypeInList(PaymentInEntity paymentin){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PaymentTypeInEntity.class);
        c.add(Restrictions.eq("paymentin", paymentin));
        c.addOrder(Order.asc("date"));
        return c.list();
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
        return c.list();
    }
}
