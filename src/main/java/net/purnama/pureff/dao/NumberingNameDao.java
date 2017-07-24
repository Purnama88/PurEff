/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.NumberingNameEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
public class NumberingNameDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<NumberingNameEntity> getActiveNumberingNameList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<NumberingNameEntity> getNumberingNameList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.addOrder(Order.asc("name"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public NumberingNameEntity getNumberingName(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        NumberingNameEntity p = (NumberingNameEntity) session.get(NumberingNameEntity.class, id);
        return p;
    }
    
    public NumberingNameEntity getNumberingNameByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.add(Restrictions.eq("name", name));
        return (NumberingNameEntity) c.uniqueResult();
    }
    
    public NumberingNameEntity addNumberingName(NumberingNameEntity numberingname) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(numberingname);
        return numberingname;
    }

    public void updateNumberingName(NumberingNameEntity numberingname) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(numberingname);
    }
    
    public List getNumberingNameList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        
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
    
    public int countNumberingNameList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
