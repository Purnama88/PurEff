/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemInvoicePurchaseEntity;
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
        return (List<ItemInvoicePurchaseEntity>)c.list();
    }
}
