/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoicePurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoicePurchaseEntity> getInvoicePurchaseList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoicePurchaseEntity> ls = session.createQuery("from InvoicePurchaseEntity").list();
        return ls;
    }
    
    public InvoicePurchaseEntity getInvoicePurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoicePurchaseEntity p = (InvoicePurchaseEntity) session.get(InvoicePurchaseEntity.class, id);
        return p;
    }
    
    public InvoicePurchaseEntity addInvoicePurchase(InvoicePurchaseEntity invoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicepurchase);
        return invoicepurchase;
    }

    public void updateInvoicePurchase(InvoicePurchaseEntity invoicepurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicepurchase);
    }
}

