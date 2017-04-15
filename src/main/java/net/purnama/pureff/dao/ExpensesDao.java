/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ExpensesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ExpensesEntity> getExpensesList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ExpensesEntity> ls = session.createQuery("from ExpensesEntity").list();
        return ls;
    }
    
    public ExpensesEntity getExpenses(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ExpensesEntity p = (ExpensesEntity) session.get(ExpensesEntity.class, id);
        return p;
    }
    
    public ExpensesEntity addExpenses(ExpensesEntity expenses) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(expenses);
        return expenses;
    }

    public void updateExpenses(ExpensesEntity expenses) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(expenses);
    }
}

