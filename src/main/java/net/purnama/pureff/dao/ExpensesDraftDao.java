/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
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
public class ExpensesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ExpensesDraftEntity> getExpensesDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ExpensesDraftEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public ExpensesDraftEntity getExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ExpensesDraftEntity p = (ExpensesDraftEntity) session.get(ExpensesDraftEntity.class, id);
        return p;
    }
    
    public ExpensesDraftEntity addExpensesDraft(ExpensesDraftEntity expensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(expensesdraft);
        return expensesdraft;
    }

    public void updateExpensesDraft(ExpensesDraftEntity expensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(expensesdraft);
    }
    
    public void deleteExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ExpensesDraftEntity p = getExpensesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getExpensesDraftList(int itemperpage, int page, String sort, 
            String keyword, UserEntity user, WarehouseEntity warehouse){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ExpensesDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.add(Restrictions.eq("warehouse", warehouse));
        
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
    
    public int countExpensesDraftList(String keyword, UserEntity user, WarehouseEntity warehouse) {
        
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ExpensesDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.add(Restrictions.eq("warehouse", warehouse));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
