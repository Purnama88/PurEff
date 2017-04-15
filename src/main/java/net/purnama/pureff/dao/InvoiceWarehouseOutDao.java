/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceWarehouseOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceWarehouseOutEntity> getInvoiceWarehouseOutList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceWarehouseOutEntity> ls = session.createQuery("from InvoiceWarehouseOutEntity").list();
        return ls;
    }
    
    public InvoiceWarehouseOutEntity getInvoiceWarehouseOut(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceWarehouseOutEntity p = (InvoiceWarehouseOutEntity) session.get(InvoiceWarehouseOutEntity.class, id);
        return p;
    }
    
    public InvoiceWarehouseOutEntity addInvoiceWarehouseOut(InvoiceWarehouseOutEntity invoicewarehouseout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicewarehouseout);
        return invoicewarehouseout;
    }

    public void updateInvoiceWarehouseOut(InvoiceWarehouseOutEntity invoicewarehouseout) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicewarehouseout);
    }
      
}
