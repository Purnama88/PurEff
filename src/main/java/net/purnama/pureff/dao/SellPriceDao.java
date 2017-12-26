/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.SellPriceEntity;
import net.purnama.pureff.entity.UomEntity;
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
public class SellPriceDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<SellPriceEntity> getSellPriceList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(SellPriceEntity.class, "sellprice");
        c.createAlias("sellprice.item", "item");
        c.createAlias("sellprice.uom", "uom");
        c.addOrder(Order.desc("item.name"));
        c.addOrder(Order.asc("uom.name"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<SellPriceEntity> getSellPriceList(ItemEntity item) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(SellPriceEntity.class);
        c.add(Restrictions.eq("item", item));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<SellPriceEntity> getSellPriceList(UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(SellPriceEntity.class);
        c.add(Restrictions.eq("uom", uom));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public SellPriceEntity getSellPrice(ItemEntity item, UomEntity uom) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(SellPriceEntity.class);
        c.add(Restrictions.eq("item", item));
        c.add(Restrictions.eq("uom", uom));
        return (SellPriceEntity)c.uniqueResult();
    }
    
    public SellPriceEntity getSellPrice(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        SellPriceEntity p = (SellPriceEntity) session.get(SellPriceEntity.class, id);
        return p;
    }
    
    public SellPriceEntity addSellPrice(SellPriceEntity sellprice) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(sellprice);
        return sellprice;
    }

    public void updateSellPrice(SellPriceEntity sellprice) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(sellprice);
    }
    
    public void deleteSellPrice(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        SellPriceEntity p = getSellPrice(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
