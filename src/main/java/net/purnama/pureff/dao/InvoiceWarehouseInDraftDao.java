/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.InvoiceWarehouseInDraftEntity;
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
    
    public List getInvoiceWarehouseInDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceWarehouseInDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.like("lastmodifiedby", user));

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
    
    public int countInvoiceWarehouseInDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceWarehouseInDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.like("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
