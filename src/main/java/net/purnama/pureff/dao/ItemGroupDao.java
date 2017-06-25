/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.ItemGroupEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class ItemGroupDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ItemGroupEntity> getActiveItemGroupList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemGroupEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        return c.list();
    }
    
    public List<ItemGroupEntity> getItemGroupList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemGroupEntity.class);
        c.addOrder(Order.asc("name"));
        return c.list();
    }
    
    public ItemGroupEntity getItemGroup(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemGroupEntity p = (ItemGroupEntity) session.get(ItemGroupEntity.class, id);
        return p;
    }
    
    public ItemGroupEntity getItemGroupByCode(String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemGroupEntity.class);
        c.add(Restrictions.eq("code", code));
        return (ItemGroupEntity)c.uniqueResult();
    }
    
    public ItemGroupEntity addItemGroup(ItemGroupEntity itemgroup) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemgroup);
        return itemgroup;
    }

    public void updateItemGroup(ItemGroupEntity itemgroup) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemgroup);
    }

    public void deleteItemGroup(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemGroupEntity p = getItemGroup(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getItemGroupList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemGroupEntity.class);
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
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countItemGroupList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemGroupEntity.class);
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
