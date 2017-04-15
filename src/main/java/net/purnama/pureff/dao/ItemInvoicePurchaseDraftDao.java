/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoicePurchaseDraftEntity;
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
public class ItemInvoicePurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoicePurchaseDraft(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicepurchasedraft);
    }
    
    public void updateItemInvoicePurchaseDraft(ItemInvoicePurchaseDraftEntity iteminvoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(iteminvoicepurchasedraft);
    }
    
    public void deleteItemInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoicePurchaseDraftEntity p = getItemInvoicePurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemInvoicePurchaseDraftEntity getItemInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoicePurchaseDraftEntity p = (ItemInvoicePurchaseDraftEntity) 
                session.get(ItemInvoicePurchaseDraftEntity.class, id);
        return p;
    }
    
    public List<ItemInvoicePurchaseDraftEntity> getItemInvoicePurchaseDraftList(InvoicePurchaseDraftEntity invoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoicePurchaseDraftEntity.class);
        c.add(Restrictions.eq("invoicepurchasedraft", invoicepurchasedraft));
        c.addOrder(Order.asc("id"));
        return (List<ItemInvoicePurchaseDraftEntity>)c.list();
    }
}
