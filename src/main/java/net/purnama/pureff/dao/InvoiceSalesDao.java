/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.InvoiceSalesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    
    public List getInvoiceSalesList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceSalesEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);

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
    
    public int countInvoiceSalesList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceSalesEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
    public List getUnpaidInvoiceSalesList(PartnerEntity partner,
            CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoiceSalesEntity.class);
        c.add(Restrictions.eq("partner", partner));
        c.add(Restrictions.eq("currency", currency));
        c.add(Restrictions.eq("status", true));
        c.add(Restrictions.gt("unpaid", 0.0));
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
}
