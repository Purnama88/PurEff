/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
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
public class ItemInvoiceWarehouseInDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceWarehouseInDraft(ItemInvoiceWarehouseInDraftEntity iteminvoicewarehouseindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicewarehouseindraft);
    }
    
    public void updateItemInvoiceWarehouseInDraft(ItemInvoiceWarehouseInDraftEntity iteminvoicewarehouseindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(iteminvoicewarehouseindraft);
    }
    
    public void deleteItemInvoiceWarehouseInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceWarehouseInDraftEntity p = getItemInvoiceWarehouseInDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemInvoiceWarehouseInDraftEntity getItemInvoiceWarehouseInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceWarehouseInDraftEntity p = (ItemInvoiceWarehouseInDraftEntity) 
                session.get(ItemInvoiceWarehouseInDraftEntity.class, id);
        return p;
    }
    
    public List<ItemInvoiceWarehouseInDraftEntity> getItemInvoiceWarehouseInDraftList(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceWarehouseInDraftEntity.class);
        c.add(Restrictions.eq("invoicewarehouseindraft", invoicewarehouseindraft));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}