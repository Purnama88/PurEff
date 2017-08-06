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
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceSalesEntity;
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
public class ItemInvoiceSalesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceSales(ItemInvoiceSalesEntity iteminvoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicesales);
    }
    
    public List<ItemInvoiceSalesEntity> getItemInvoiceSalesList(InvoiceSalesEntity invoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceSalesEntity.class);
        c.add(Restrictions.eq("invoicesales", invoicesales));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemInvoiceSalesEntity>
         getItemInvoiceSalesList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner,
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemInvoiceSalesEntity.class, "iteminvoicesales");
        c.createAlias("iteminvoicesales.invoicesales", "invoicesales");
        c.add(Restrictions.between("invoicesales.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("invoicesales.warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("invoicesales.currency", currency));
        }
        if(partner != null){
            c.add(Restrictions.eq("invoicesales.partner", partner));
        }
        c.add(Restrictions.eq("invoicesales.status", status));
        c.addOrder(Order.asc("invoicesales.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}