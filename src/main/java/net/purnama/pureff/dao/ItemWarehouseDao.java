/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ItemWarehouseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ItemWarehouseEntity> getItemWarehouseList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class, "itemwarehouse");
        c.createAlias("itemwarehouse.warehouse", "warehouse");
        c.createAlias("itemwarehouse.item", "item");
        c.addOrder(Order.desc("item.name"));
        c.addOrder(Order.asc("warehouse.code"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public ItemWarehouseEntity getItemWarehouse(WarehouseEntity warehouse, ItemEntity item) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
        c.add(Restrictions.eq("item", item));
        return (ItemWarehouseEntity)c.uniqueResult();
    }
    
    public ItemWarehouseEntity addItemWarehouse(ItemWarehouseEntity partnertype) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(partnertype);
        return partnertype;
    }

    public void updateItemWarehouse(ItemWarehouseEntity partnertype) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(partnertype);
    }
    
    public List getItemWarehouseList(WarehouseEntity warehouse, int itemperpage,
            int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
        
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,desc);
        Criteria nc = c.createCriteria("item");
        nc.add(orExp);
        
        if(sort.contains("-")){
            nc.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            nc.addOrder(Order.asc(sort));
        }
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countItemWarehouseList(WarehouseEntity warehouse, String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion description = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,description);
        c.createCriteria("item").add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
    public List getItemWarehouseList(WarehouseEntity warehouse, ItemGroupEntity itemgroup,
            boolean status){
        
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class);
        if(warehouse != null){
            c.add(Restrictions.eq("warehouse", warehouse));
        }
        
        Criteria nc = c.createCriteria("item");
        if(itemgroup != null){
            nc.add(Restrictions.eq("itemgroup", itemgroup));
        }
        nc.add(Restrictions.eq("status", status));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    } 
}
