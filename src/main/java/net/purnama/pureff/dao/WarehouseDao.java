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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
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
        Criteria cr = session.createCriteria(WarehouseEntity.class);
        
        cr.add(Restrictions.eq("status", true));
        
        cr.setProjection(Projections.projectionList()
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
        c.addOrder(Order.asc("code"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<WarehouseEntity> getWarehouseList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(WarehouseEntity.class);
        c.addOrder(Order.asc("code"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public WarehouseEntity getWarehouse(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        WarehouseEntity p = (WarehouseEntity) session.get(WarehouseEntity.class, id);
        return p;
    }
    
    public WarehouseEntity getWarehouseByCode(String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(WarehouseEntity.class);
        c.add(Restrictions.eq("code", code));
        return (WarehouseEntity)c.uniqueResult();
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
    
    public List getWarehouseList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(WarehouseEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,desc);
        c.add(orExp);
        
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
    
    public int countWarehouseList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(WarehouseEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion description = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,description);
        c.add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
}
