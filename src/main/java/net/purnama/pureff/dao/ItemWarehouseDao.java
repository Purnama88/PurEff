/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.WarehouseEntity;
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
public class ItemWarehouseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ItemWarehouseEntity> getItemWarehouseList(WarehouseEntity warehouse) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemWarehouseEntity.class);
        c.add(Restrictions.eq("warehouse", warehouse));
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
}
