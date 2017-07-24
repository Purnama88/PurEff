/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
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
public class ItemReturnPurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemReturnPurchase(ItemReturnPurchaseEntity itemreturnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemreturnpurchase);
    }
    
    public List<ItemReturnPurchaseEntity> getItemReturnPurchaseList(ReturnPurchaseEntity returnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemReturnPurchaseEntity.class);
        c.add(Restrictions.eq("returnpurchase", returnpurchase));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
