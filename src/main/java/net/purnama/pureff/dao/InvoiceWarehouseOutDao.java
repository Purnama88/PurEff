/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.InvoiceWarehouseOutEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
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
public class InvoiceWarehouseOutDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<InvoiceWarehouseOutEntity> getInvoiceWarehouseOutList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceWarehouseOutEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
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
      
    public List getInvoiceWarehouseOutList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceWarehouseOutEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("destination_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("shipping_number", "%"+keyword+"%"));
        
        c.add(disjunction);
        
        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countInvoiceWarehouseOutList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceWarehouseOutEntity.class);

        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("destination_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("shipping_number", "%"+keyword+"%"));
        
        c.add(disjunction);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
