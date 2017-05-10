/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemDeliveryDraftEntity;
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
public class ItemDeliveryDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<ItemDeliveryDraftEntity> getItemDeliveryDraftList(DeliveryDraftEntity deliverydraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemDeliveryDraftEntity.class);
        c.add(Restrictions.eq("deliverydraft", deliverydraft));
        c.addOrder(Order.asc("id"));
        return (List<ItemDeliveryDraftEntity>)c.list();
    }
    
    public ItemDeliveryDraftEntity getItemDeliveryDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemDeliveryDraftEntity p = (ItemDeliveryDraftEntity) session.get(ItemDeliveryDraftEntity.class, id);
        return p;
    }
    
    public ItemDeliveryDraftEntity addItemDeliveryDraft(ItemDeliveryDraftEntity itemdeliverydraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemdeliverydraft);
        return itemdeliverydraft;
    }
    
    public void updateItemDeliveryDraft(ItemDeliveryDraftEntity itemdeliverydraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemdeliverydraft);
    }
    
    public void deleteItemDeliveryDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemDeliveryDraftEntity p = getItemDeliveryDraft(id);
        if (null != p) {
            session.delete(p);
        }
    }
}