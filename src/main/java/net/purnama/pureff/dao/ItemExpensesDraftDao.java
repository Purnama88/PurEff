/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemExpensesDraftEntity;
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
public class ItemExpensesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemExpensesDraft(ItemExpensesDraftEntity itemexpensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemexpensesdraft);
    }
    
    public void updateItemExpensesDraft(ItemExpensesDraftEntity itemexpensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemexpensesdraft);
    }
    
    public void deleteItemExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemExpensesDraftEntity p = getItemExpensesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemExpensesDraftEntity getItemExpensesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemExpensesDraftEntity p = (ItemExpensesDraftEntity) 
                session.get(ItemExpensesDraftEntity.class, id);
        return p;
    }
    
    public List<ItemExpensesDraftEntity> getItemExpensesDraftList(ExpensesDraftEntity expensesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemExpensesDraftEntity.class);
        c.add(Restrictions.eq("expensesdraft", expensesdraft));
        c.addOrder(Order.asc("id"));
        return (List<ItemExpensesDraftEntity>)c.list();
    }
}