/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<ItemExpensesEntity>
         getItemExpensesList(Calendar start, Calendar end, WarehouseEntity warehouse,
                 PartnerEntity partner,
                 CurrencyEntity currency, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        
        Criteria c = session.createCriteria(ItemExpensesEntity.class, "itemexpenses");
        c.createAlias("itemexpenses.expenses", "expenses");
        c.add(Restrictions.between("expenses.date", start, end));
        if(warehouse != null){
            c.add(Restrictions.eq("expenses.warehouse", warehouse));
        }
        if(partner != null){
            c.add(Restrictions.eq("expenses.partner", partner));
        }
        if(currency != null){
            c.add(Restrictions.eq("expenses.currency", currency));
        }
        c.add(Restrictions.eq("expenses.status", status));
        c.addOrder(Order.asc("expenses.date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}