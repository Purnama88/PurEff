/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.WarehouseEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class WarehouseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<WarehouseEntity> getWarehouse_IdCode_List(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(WarehouseEntity.class)
        .setProjection(Projections.projectionList()
        .add(Projections.property("id"), "id")
        .add(Projections.property("code"), "code"))
        .setResultTransformer(Transformers.aliasToBean(WarehouseEntity.class));

        List<WarehouseEntity> list = cr.list();
        
        return list;
    }
    
    public List<WarehouseEntity> getActiveWarehouseList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(WarehouseEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<WarehouseEntity> getWarehouseList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<WarehouseEntity> ls = session.createQuery("from WarehouseEntity").list();
        return ls;
    }
    
    public WarehouseEntity getWarehouse(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        WarehouseEntity p = (WarehouseEntity) session.get(WarehouseEntity.class, id);
        return p;
    }
    
    public WarehouseEntity addWarehouse(WarehouseEntity warehouse) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(warehouse);
        return warehouse;
    }
    
    public void updateWarehouse(WarehouseEntity warehouse) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(warehouse);
    }
    
}
