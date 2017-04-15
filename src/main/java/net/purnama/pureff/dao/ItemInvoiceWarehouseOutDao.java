/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import net.purnama.pureff.entity.transactional.ItemInvoiceWarehouseOutEntity;
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
public class ItemInvoiceWarehouseOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<ItemInvoiceWarehouseOutEntity> getItemInvoiceWarehouseOutList(InvoiceWarehouseOutEntity invoicewarehouseout) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceWarehouseOutEntity.class);
        c.add(Restrictions.eq("invoicewarehouseout", invoicewarehouseout));
        c.addOrder(Order.asc("id"));
        return (List<ItemInvoiceWarehouseOutEntity>)c.list();
    }
}
