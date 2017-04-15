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
}
