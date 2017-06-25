/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.InvoicePurchaseEntity;
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
    
    public List getInvoicePurchaseList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseEntity.class);
        
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
    
    public int countInvoicePurchaseList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseEntity.class);
        
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
    
    public List getUnpaidInvoicePurchaseList(PartnerEntity partner,
            CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseEntity.class);
        c.add(Restrictions.eq("partner", partner));
        c.add(Restrictions.eq("currency", currency));
        c.add(Restrictions.eq("status", true));
        c.add(Restrictions.gt("remaining", 0.0));
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
    
    public List getInvoicePurchaseList(Calendar begin, Calendar end,
            WarehouseEntity warehouse, PartnerEntity partner, CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(InvoicePurchaseEntity.class);
        c.add(Restrictions.between("date", begin, end));
        if(partner != null){
            c.add(Restrictions.eq("partner", partner));
        }
        if(warehouse != null){
            c.add(Restrictions.eq("warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("currency", currency));
        }
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
}

