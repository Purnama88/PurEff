/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.BuyPriceEntity;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;
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
public class BuyPriceDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<BuyPriceEntity> getBuyPriceList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<BuyPriceEntity> ls = session.createQuery("from BuyPriceEntity").list();
        return ls;
    }
    
    public List<BuyPriceEntity> getBuyPriceList(ItemEntity item) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(BuyPriceEntity.class);
        c.add(Restrictions.eq("item", item));
        return c.list();
    }
    
    public List<BuyPriceEntity> getBuyPriceList(UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(BuyPriceEntity.class);
        c.add(Restrictions.eq("uom", uom));
        return c.list();
    }
    
    public BuyPriceEntity getBuyPrice(ItemEntity item, UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(BuyPriceEntity.class);
        c.add(Restrictions.eq("item", item));
        c.add(Restrictions.eq("uom", uom));
        return (BuyPriceEntity)c.uniqueResult();
    }
    
    public BuyPriceEntity getBuyPrice(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        BuyPriceEntity p = (BuyPriceEntity) session.get(BuyPriceEntity.class, id);
        return p;
    }
    
    public BuyPriceEntity addBuyPrice(BuyPriceEntity buyprice) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(buyprice);
        return buyprice;
    }

    public void updateBuyPrice(BuyPriceEntity buyprice) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(buyprice);
    }
    
    public void deleteBuyPrice(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        BuyPriceEntity p = getBuyPrice(id);
        if (null != p) {
                session.delete(p);
        }
    }
}

