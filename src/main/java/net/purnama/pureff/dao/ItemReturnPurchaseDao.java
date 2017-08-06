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
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
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
    
    public List<ItemReturnPurchaseEntity>
         getItemReturnPurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner, 
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemReturnPurchaseEntity.class, "itemreturnpurchase");
        c.createAlias("itemreturnpurchase.returnpurchase", "returnpurchase");
        c.add(Restrictions.between("returnpurchase.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("returnpurchase.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("returnpurchase.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("returnpurchase.partner", partner));
        }
        c.add(Restrictions.eq("returnpurchase.status", status));
        c.addOrder(Order.asc("returnpurchase.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
