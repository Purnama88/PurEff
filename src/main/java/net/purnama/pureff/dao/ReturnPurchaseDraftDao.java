/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
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
public class ReturnPurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnPurchaseDraftEntity> getReturnPurchaseDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseDraftEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public ReturnPurchaseDraftEntity getReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseDraftEntity p = (ReturnPurchaseDraftEntity) session.get(ReturnPurchaseDraftEntity.class, id);
        return p;
    }
    
    public ReturnPurchaseDraftEntity addReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnpurchasedraft);
        return returnpurchasedraft;
    }

    public void updateReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnpurchasedraft);
    }
    
    public void deleteReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseDraftEntity p = getReturnPurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getReturnPurchaseDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        
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
    
    public int countReturnPurchaseDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnPurchaseDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}

