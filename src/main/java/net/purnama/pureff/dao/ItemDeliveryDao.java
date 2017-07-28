/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;
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
public class ItemDeliveryDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemDelivery(ItemDeliveryEntity itemdelivery) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemdelivery);
    }
    
    public List<ItemDeliveryEntity> getItemDeliveryList(DeliveryEntity delivery) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemDeliveryEntity.class);
        c.add(Restrictions.eq("delivery", delivery));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemDeliveryEntity>
         getItemDeliveryList(Calendar start, Calendar end, WarehouseEntity warehouse, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemDeliveryEntity.class, "itemdelivery");
        c.createAlias("itemdelivery.delivery", "delivery");
        c.add(Restrictions.between("delivery.date", start, end));
        c.add(Restrictions.eq("delivery.warehouse", warehouse));
        c.add(Restrictions.eq("delivery.status", status));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
