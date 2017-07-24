/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
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
}
