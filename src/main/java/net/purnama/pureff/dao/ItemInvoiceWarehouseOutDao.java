/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
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
public class ItemInvoiceWarehouseOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceWarehouseOut(ItemInvoiceWarehouseOutEntity iteminvoicewarehouseout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicewarehouseout);
    }
    
    public List<ItemInvoiceWarehouseOutEntity> getItemInvoiceWarehouseOutList(InvoiceWarehouseOutEntity invoicewarehouseout) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceWarehouseOutEntity.class);
        c.add(Restrictions.eq("invoicewarehouseout", invoicewarehouseout));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemInvoiceWarehouseOutEntity>
         getItemInvoiceWarehouseOutList(Calendar start, Calendar end, WarehouseEntity warehouse, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemInvoiceWarehouseOutEntity.class, "iteminvoicewarehouseout");
        c.createAlias("iteminvoicewarehouseout.invoicewarehouseout", "invoicewarehouseout");
        c.add(Restrictions.between("invoicewarehouseout.date", start, end));
        c.add(Restrictions.eq("invoicewarehouseout.warehouse", warehouse));
        c.add(Restrictions.eq("invoicewarehouseout.status", status));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
