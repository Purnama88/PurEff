/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
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
public class ItemDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ItemEntity> getItemList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ItemEntity> ls = session.createQuery("from ItemEntity").list();
        return ls;
    }
    
    public List<ItemEntity> getActiveItemList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public ItemEntity getItem(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemEntity p = (ItemEntity) session.get(ItemEntity.class, id);
        return p;
    }
    
    public ItemEntity getItemByCode(String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemEntity.class);
        c.add(Restrictions.eq("code", code));
        return (ItemEntity)c.uniqueResult();
    }
    
    public ItemEntity addItem(ItemEntity item) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(item);
        return item;
    }

    public void updateItem(ItemEntity item) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(item);
    }
}
