/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnSalesDraftEntity;
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
public class ItemReturnSalesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemReturnSalesDraft(ItemReturnSalesDraftEntity itemreturnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemreturnsalesdraft);
    }
    
    public void updateItemReturnSalesDraft(ItemReturnSalesDraftEntity itemreturnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemreturnsalesdraft);
    }
    
    public void deleteItemReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemReturnSalesDraftEntity p = getItemReturnSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemReturnSalesDraftEntity getItemReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemReturnSalesDraftEntity p = (ItemReturnSalesDraftEntity) 
                session.get(ItemReturnSalesDraftEntity.class, id);
        return p;
    }
    
    public List<ItemReturnSalesDraftEntity> getItemReturnSalesDraftList(ReturnSalesDraftEntity returnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemReturnSalesDraftEntity.class);
        c.add(Restrictions.eq("returnsalesdraft", returnsalesdraft));
        c.addOrder(Order.asc("id"));
        return (List<ItemReturnSalesDraftEntity>)c.list();
    }
}
