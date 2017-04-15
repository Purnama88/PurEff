/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ItemExpensesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemExpenses(ItemExpensesEntity itemexpenses) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemexpenses);
    }
    
    public List<ItemExpensesEntity> getItemExpensesList(ExpensesEntity expenses) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemExpensesEntity.class);
        c.add(Restrictions.eq("expenses", expenses));
        c.addOrder(Order.asc("id"));
        return (List<ItemExpensesEntity>)c.list();
    }
}