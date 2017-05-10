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
        c.addOrder(Order.asc("name"));
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
    
    public UomEntity getUomByName(String name){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        c.add(Restrictions.eq("name", name));
        return (UomEntity)c.uniqueResult();
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
    
    public List getUomList(int itemperpage, int page, String sort, String keyword, UomEntity parent){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        if(parent == null){
            c.add(Restrictions.isNull("parent"));
        }
        else{
            c.add(Restrictions.eq("parent", parent));
        }
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        
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
    
    public int countUomList(String keyword, UomEntity parent){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UomEntity.class);
        if(parent == null){
            c.add(Restrictions.isNull("parent"));
        }
        else{
            c.add(Restrictions.eq("parent", parent));
        }
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
