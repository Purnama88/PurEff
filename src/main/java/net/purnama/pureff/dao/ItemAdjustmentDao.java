/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
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
public class ItemAdjustmentDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemAdjustment(ItemAdjustmentEntity itemadjustment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemadjustment);
    }
    
    public List<ItemAdjustmentEntity> getItemAdjustmentList(AdjustmentEntity adjustment) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemAdjustmentEntity.class);
        c.add(Restrictions.eq("adjustment", adjustment));
        c.addOrder(Order.asc("id"));
        return (List<ItemAdjustmentEntity>)c.list();
    }
}
