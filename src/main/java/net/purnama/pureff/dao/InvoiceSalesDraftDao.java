/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoiceSalesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceSalesDraftEntity> getInvoiceSalesDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoiceSalesDraftEntity> ls = session.createQuery("from InvoiceSalesDraftEntity").list();
        return ls;
    }
    
    public InvoiceSalesDraftEntity getInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceSalesDraftEntity p = (InvoiceSalesDraftEntity) session.get(InvoiceSalesDraftEntity.class, id);
        return p;
    }
    
    public InvoiceSalesDraftEntity addInvoiceSalesDraft(InvoiceSalesDraftEntity invoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicesalesdraft);
        return invoicesalesdraft;
    }

    public void updateInvoiceSalesDraft(InvoiceSalesDraftEntity invoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicesalesdraft);
    }
    
    public void deleteInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoiceSalesDraftEntity p = getInvoiceSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
