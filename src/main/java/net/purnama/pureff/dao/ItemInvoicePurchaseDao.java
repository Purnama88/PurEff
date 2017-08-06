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
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
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
public class ItemInvoicePurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoicePurchase(ItemInvoicePurchaseEntity iteminvoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicepurchase);
    }
    
    public List<ItemInvoicePurchaseEntity> getItemInvoicePurchaseList(InvoicePurchaseEntity invoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoicePurchaseEntity.class);
        c.add(Restrictions.eq("invoicepurchase", invoicepurchase));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemInvoicePurchaseEntity>
         getItemInvoicePurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse,
                 PartnerEntity partner, 
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemInvoicePurchaseEntity.class, "iteminvoicepurchase");
        c.createAlias("iteminvoicepurchase.invoicepurchase", "invoicepurchase");
        c.add(Restrictions.between("invoicepurchase.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("invoicepurchase.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("invoicepurchase.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("invoicepurchase.partner", currency));
        }
        c.add(Restrictions.eq("invoicepurchase.status", status));
        c.addOrder(Order.asc("invoicepurchase.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
