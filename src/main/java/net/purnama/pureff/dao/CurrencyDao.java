/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class CurrencyDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public CurrencyEntity getDefaultCurrency(){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(CurrencyEntity.class);
        c.add(Restrictions.eq("defaultcurrency", true));
        CurrencyEntity currency = (CurrencyEntity)c.uniqueResult();
        return currency;
    }
    
    public List<CurrencyEntity> getCurrencyList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CurrencyEntity> ls = session.createQuery("from CurrencyEntity").list();
        return ls;
    }
    
    public CurrencyEntity getCurrencyByCode(String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(CurrencyEntity.class);
        c.add(Restrictions.eq("code", code));
        return (CurrencyEntity)c.uniqueResult();
    }
    
    public CurrencyEntity getCurrency(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        CurrencyEntity p = (CurrencyEntity) session.get(CurrencyEntity.class, id);
        return p;
    }
    
    public CurrencyEntity addCurrency(CurrencyEntity currency) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(currency);
        return currency;
    }

    public void updateCurrency(CurrencyEntity currency) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(currency);
    }
    
    public List getCurrencyList(int itemperpage, int page, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(CurrencyEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,desc);
        c.add(orExp);

        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countCurrencyList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(CurrencyEntity.class);
        Criterion code = Restrictions.like("code", "%"+keyword+"%");
        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(code,desc);
        c.add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
