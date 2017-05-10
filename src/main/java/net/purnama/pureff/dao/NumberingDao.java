/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.NumberingNameEntity;
import net.purnama.pureff.entity.WarehouseEntity;
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
public class NumberingDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<NumberingEntity> getNumberingList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<NumberingEntity> ls = session.createQuery("from NumberingEntity").list();
        return ls;
    }
    
    public List<NumberingEntity> getActiveNumberingList(WarehouseEntity warehouse) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
        return c.list();
    }
    
    public List<NumberingEntity> getNumberingList(WarehouseEntity warehouse, MenuEntity menu) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
        c.add(Restrictions.eq("menu", menu));
        c.addOrder(Order.asc("prefix"));
        return c.list();
    }
    
    public NumberingEntity getDefaultNumbering(MenuEntity menu, WarehouseEntity warehouse){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingEntity.class);
        c.add(Restrictions.eq("menu", menu));
        c.add(Restrictions.eq("warehouse", warehouse));
        c.add(Restrictions.eq("status", true));
        return (NumberingEntity)c.uniqueResult();
    }
    
    public NumberingEntity getNumbering(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        NumberingEntity p = (NumberingEntity) session.get(NumberingEntity.class, id);
        return p;
    }
    
    public NumberingEntity getNumbering(String prefix, NumberingNameEntity numberingname,
            WarehouseEntity warehouse, MenuEntity menu) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingEntity.class);
        c.add(Restrictions.eq("prefix", prefix));
        c.add(Restrictions.eq("numberingname", numberingname));
        c.add(Restrictions.eq("warehouse", warehouse));
        c.add(Restrictions.eq("menu", menu));
        return (NumberingEntity)c.uniqueResult();
    }
    
    public NumberingEntity addNumbering(NumberingEntity numbering) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(numbering);
        return numbering;
    }

    public void updateNumbering(NumberingEntity numbering) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(numbering);
    }
}
