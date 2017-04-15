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
        return c.list();
    }
    
    public List<ItemGroupEntity> getItemGroupList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ItemGroupEntity> ls = session.createQuery("from ItemGroupEntity").list();
        return ls;
    }
    
    public ItemGroupEntity getItemGroup(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemGroupEntity p = (ItemGroupEntity) session.get(ItemGroupEntity.class, id);
        return p;
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
}
