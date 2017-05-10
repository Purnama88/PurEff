/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.InvoicePurchaseDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class InvoicePurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoicePurchaseDraftEntity> getInvoicePurchaseDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<InvoicePurchaseDraftEntity> ls = session.createQuery("from InvoicePurchaseDraftEntity").list();
        return ls;
    }
    
    public InvoicePurchaseDraftEntity getInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoicePurchaseDraftEntity p = (InvoicePurchaseDraftEntity) session.get(InvoicePurchaseDraftEntity.class, id);
        return p;
    }
    
    public InvoicePurchaseDraftEntity addInvoicePurchaseDraft(InvoicePurchaseDraftEntity invoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(invoicepurchasedraft);
        return invoicepurchasedraft;
    }

    public void updateInvoicePurchaseDraft(InvoicePurchaseDraftEntity invoicepurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(invoicepurchasedraft);
    }
    
    public void deleteInvoicePurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        InvoicePurchaseDraftEntity p = getInvoicePurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getInvoicePurchaseDraftList(int itemperpage, int page, 
            String sort, String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        
        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countInvoicePurchaseDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
