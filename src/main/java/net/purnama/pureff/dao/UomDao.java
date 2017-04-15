/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UomEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class UomDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<UomEntity> getParentUomList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        c.add(Restrictions.isNull("parent"));
        return c.list();
    }
    
    public List<UomEntity> getActiveParentUomList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        c.add(Restrictions.isNull("parent"));
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<UomEntity> getUomList(UomEntity parent){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        c.add(Restrictions.eq("parent", parent));
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<UomEntity> getUomList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<UomEntity> ls = session.createQuery("from UomEntity").list();
        return ls;
    }
    
    public List<UomEntity> getActiveUomList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public UomEntity getUom(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        UomEntity p = (UomEntity) session.get(UomEntity.class, id);
        return p;
    }
    
    public UomEntity addUom(UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(uom);
        return uom;
    }

    public void updateUom(UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(uom);
    }
}
