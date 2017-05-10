/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemAdjustmentDraftEntity;
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
public class ItemAdjustmentDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemAdjustmentDraft(ItemAdjustmentDraftEntity itemadjustmentdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemadjustmentdraft);
    }
    
    public void updateItemAdjustmentDraft(ItemAdjustmentDraftEntity itemadjustmentdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemadjustmentdraft);
    }
    
    public void deleteItemAdjustmentDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemAdjustmentDraftEntity p = getItemAdjustmentDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemAdjustmentDraftEntity getItemAdjustmentDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemAdjustmentDraftEntity p = (ItemAdjustmentDraftEntity) 
                session.get(ItemAdjustmentDraftEntity.class, id);
        return p;
    }
    
    public List<ItemAdjustmentDraftEntity> getItemAdjustmentDraftList(AdjustmentDraftEntity adjustmentdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemAdjustmentDraftEntity.class);
        c.add(Restrictions.eq("adjustmentdraft", adjustmentdraft));
        c.addOrder(Order.asc("id"));
        return (List<ItemAdjustmentDraftEntity>)c.list();
    }
}
