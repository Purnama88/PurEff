/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        List<ExpensesDraftEntity> ls = session.createQuery("from ExpensesDraftEntity").list();
        return ls;
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
}
