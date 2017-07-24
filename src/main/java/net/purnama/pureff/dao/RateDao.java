/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.RateEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class RateDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public RateEntity getLastRate(CurrencyEntity currency){
        DetachedCriteria maxQuery = DetachedCriteria.forClass(RateEntity.class);
        maxQuery.add(Restrictions.eq("currency", currency));
        maxQuery.setProjection(Projections.max("lastmodified"));
        
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RateEntity.class);
        c.add(Restrictions.eq("currency", currency));
        c.add(Property.forName("lastmodified").eq(maxQuery));
        
        RateEntity rate = (RateEntity) c.uniqueResult();
        
        return rate;
    }
    
    public List<RateEntity> getRateList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RateEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public RateEntity getRate(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        RateEntity p = (RateEntity) session.get(RateEntity.class, id);
        return p;
    }
    
    public RateEntity addRate(RateEntity rate) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(rate);
        return rate;
    }

    public void updateRate(RateEntity rate) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(rate);
    }
    
    public List getRateList(CurrencyEntity currency, int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RateEntity.class);
        c.add(Restrictions.eq("currency", currency));
        
//        Criterion code = Restrictions.like("code", "%"+keyword+"%");
//        Criterion desc = Restrictions.like("name", "%"+keyword+"%");
//        LogicalExpression orExp = Restrictions.or(code,desc);
//        c.add(orExp);
        
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
    
    public int countRateList(CurrencyEntity currency, String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RateEntity.class);
        c.add(Restrictions.eq("currency", currency));
        
//        Criterion code = Restrictions.like("code", "%"+keyword+"%");
//        Criterion description = Restrictions.like("name", "%"+keyword+"%");
//        LogicalExpression orExp = Restrictions.or(code,description);
//        c.add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}