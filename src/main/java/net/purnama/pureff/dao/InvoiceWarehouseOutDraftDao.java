/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseOutDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceWarehouseOutDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceWarehouseOutDraftEntity> getInvoiceWarehouseOutDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceWarehouseOutDraftEntity> ls = session.createQuery("from InvoiceWarehouseOutDraftEntity").list();
        return ls;
    }
    
    public InvoiceWarehouseOutDraftEntity getInvoiceWarehouseOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseOutDraftEntity p = (InvoiceWarehouseOutDraftEntity) session.get(InvoiceWarehouseOutDraftEntity.class, id);
        return p;
    }
    
    public InvoiceWarehouseOutDraftEntity addInvoiceWarehouseOutDraft(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicewarehouseoutdraft);
        return invoicewarehouseoutdraft;
    }

    public void updateInvoiceWarehouseOutDraft(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicewarehouseoutdraft);
    }
    
    public void deleteInvoiceWarehouseOutDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseOutDraftEntity p = getInvoiceWarehouseOutDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
