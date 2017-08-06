/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
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
public class ItemReturnSalesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemReturnSales(ItemReturnSalesEntity itemreturnsales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemreturnsales);
    }
    
    public List<ItemReturnSalesEntity> getItemReturnSalesList(ReturnSalesEntity returnsales) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemReturnSalesEntity.class);
        c.add(Restrictions.eq("returnsales", returnsales));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemReturnSalesEntity>
         getItemReturnSalesList(Calendar start, Calendar end, WarehouseEntity warehouse,
                 PartnerEntity partner,
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemReturnSalesEntity.class, "itemreturnsales");
        c.createAlias("itemreturnsales.returnsales", "returnsales");
        c.add(Restrictions.between("returnsales.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("returnsales.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("returnsales.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("returnsales.partner", partner));
        }
        c.add(Restrictions.eq("returnsales.status", status));
        c.addOrder(Order.asc("returnsales.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
