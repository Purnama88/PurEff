/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseInEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceWarehouseInDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceWarehouseInEntity> getInvoiceWarehouseInList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceWarehouseInEntity> ls = session.createQuery("from InvoiceWarehouseInEntity").list();
        return ls;
    }
    
    public InvoiceWarehouseInEntity getInvoiceWarehouseIn(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseInEntity p = (InvoiceWarehouseInEntity) session.get(InvoiceWarehouseInEntity.class, id);
        return p;
    }
    
    public InvoiceWarehouseInEntity addInvoiceWarehouseIn(InvoiceWarehouseInEntity invoicewarehousein) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicewarehousein);
        return invoicewarehousein;
    }

    public void updateInvoiceWarehouseIn(InvoiceWarehouseInEntity invoicewarehousein) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicewarehousein);
    }
}
