/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
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
public class ItemInvoiceWarehouseOutDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceWarehouseOutDraft(ItemInvoiceWarehouseOutDraftEntity iteminvoicewarehouseoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicewarehouseoutdraft);
    }
    
    public void updateItemInvoiceWarehouseOutDraft(ItemInvoiceWarehouseOutDraftEntity iteminvoicewarehouseoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(iteminvoicewarehouseoutdraft);
    }
    
    public void deleteItemInvoiceWarehouseOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceWarehouseOutDraftEntity p = getItemInvoiceWarehouseOutDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemInvoiceWarehouseOutDraftEntity getItemInvoiceWarehouseOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceWarehouseOutDraftEntity p = (ItemInvoiceWarehouseOutDraftEntity) 
                session.get(ItemInvoiceWarehouseOutDraftEntity.class, id);
        return p;
    }
    
    public List<ItemInvoiceWarehouseOutDraftEntity> getItemInvoiceWarehouseOutDraftList(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceWarehouseOutDraftEntity.class);
        c.add(Restrictions.eq("invoicewarehouseoutdraft", invoicewarehouseoutdraft));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}