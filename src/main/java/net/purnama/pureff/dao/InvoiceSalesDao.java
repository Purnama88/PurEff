/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceSalesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceSalesEntity> getInvoiceSalesList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceSalesEntity> ls = session.createQuery("from InvoiceSalesEntity").list();
        return ls;
    }
    
    public InvoiceSalesEntity getInvoiceSales(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceSalesEntity p = (InvoiceSalesEntity) session.get(InvoiceSalesEntity.class, id);
        return p;
    }
    
    public InvoiceSalesEntity addInvoiceSales(InvoiceSalesEntity invoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicesales);
        return invoicesales;
    }

    public void updateInvoiceSales(InvoiceSalesEntity invoicesales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicesales);
    }
}
