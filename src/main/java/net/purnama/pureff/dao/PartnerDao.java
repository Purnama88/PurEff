/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.PartnerEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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
public class PartnerDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PartnerEntity> getActivePartnerList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<PartnerEntity> getPartnerList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PartnerEntity> ls = session.createQuery("from PartnerEntity").list();
        return ls;
    }
    
    public PartnerEntity getPartner(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PartnerEntity p = (PartnerEntity) session.get(PartnerEntity.class, id);
        return p;
    }
    
    public PartnerEntity getPartnerByCode(String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("code", code));
        return (PartnerEntity)c.uniqueResult();
    }
    
    public PartnerEntity addPartner(PartnerEntity partner) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(partner);
        return partner;
    }

    public void updatePartner(PartnerEntity partner) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(partner);
    }
    
    public List getPartnerList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,desc);
        c.add(orExp);
        
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
    
    public int countPartnerList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion description = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,description);
        c.add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
    public List getVendorList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 1));
        List ls = c.list();
        return ls;
    }
    
    public List getActiveVendorList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 1));
        List ls = c.list();
        return ls;
    }
    
    public List getCustomerList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 0));
        List ls = c.list();
        return ls;
    }
    
    public List getActiveCustomerList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 0));
        List ls = c.list();
        return ls;
    }
    
    public List getNonTradeList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 2));
        List ls = c.list();
        return ls;
    }
    
    public List getActiveNonTradeList(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        c.createCriteria("partnertype").add(Restrictions.eq("parent", 2));
        List ls = c.list();
        return ls;
    }
}
