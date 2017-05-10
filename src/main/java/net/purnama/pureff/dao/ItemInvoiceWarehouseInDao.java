/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseInEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ItemInvoiceWarehouseInDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceWarehouseIn(ItemInvoiceWarehouseInEntity iteminvoicewarehousein) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicewarehousein);
    }
    
    public List<ItemInvoiceWarehouseInEntity> getItemInvoiceWarehouseInList(InvoiceWarehouseInEntity invoicewarehousein) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceWarehouseInEntity.class);
        c.add(Restrictions.eq("invoicewarehousein", invoicewarehousein));
        c.addOrder(Order.asc("id"));
        return (List<ItemInvoiceWarehouseInEntity>)c.list();
    }
}

