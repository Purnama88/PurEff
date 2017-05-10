/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
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
public class ReturnPurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnPurchaseEntity> getReturnPurchaseList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReturnPurchaseEntity> ls = session.createQuery("from ReturnPurchaseEntity").list();
        return ls;
    }
    
    public ReturnPurchaseEntity getReturnPurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseEntity p = (ReturnPurchaseEntity) session.get(ReturnPurchaseEntity.class, id);
        return p;
    }
    
    public ReturnPurchaseEntity addReturnPurchase(ReturnPurchaseEntity returnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnpurchase);
        return returnpurchase;
    }

    public void updateReturnPurchase(ReturnPurchaseEntity returnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnpurchase);
    }
    
    public List getReturnPurchaseList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseEntity.class);
        
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
    
    public int countReturnPurchaseList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseEntity.class);
        
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
    
    public List getUnpaidReturnPurchaseList(PartnerEntity partner,
            CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseEntity.class);
        c.add(Restrictions.eq("partner", partner));
        c.add(Restrictions.eq("currency", currency));
        c.add(Restrictions.eq("status", true));
        c.add(Restrictions.gt("unpaid", 0.0));
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
}