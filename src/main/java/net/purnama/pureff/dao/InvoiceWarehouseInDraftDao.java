/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceWarehouseInDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceWarehouseInDraftEntity> getInvoiceWarehouseInDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceWarehouseInDraftEntity> ls = session.createQuery("from InvoiceWarehouseInDraftEntity").list();
        return ls;
    }
    
    public InvoiceWarehouseInDraftEntity getInvoiceWarehouseInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseInDraftEntity p = (InvoiceWarehouseInDraftEntity) session.get(InvoiceWarehouseInDraftEntity.class, id);
        return p;
    }
    
    public InvoiceWarehouseInDraftEntity addInvoiceWarehouseInDraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicewarehouseindraft);
        return invoicewarehouseindraft;
    }

    public void updateInvoiceWarehouseInDraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicewarehouseindraft);
    }
    
    public void deleteInvoiceWarehouseInDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseInDraftEntity p = getInvoiceWarehouseInDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
