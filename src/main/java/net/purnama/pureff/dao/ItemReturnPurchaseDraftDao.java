/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnPurchaseDraftEntity;
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
public class ItemReturnPurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemReturnPurchaseDraft(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(itemreturnpurchasedraft);
    }
    
    public void updateItemReturnPurchaseDraft(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(itemreturnpurchasedraft);
    }
    
    public void deleteItemReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemReturnPurchaseDraftEntity p = getItemReturnPurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemReturnPurchaseDraftEntity getItemReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemReturnPurchaseDraftEntity p = (ItemReturnPurchaseDraftEntity) 
                session.get(ItemReturnPurchaseDraftEntity.class, id);
        return p;
    }
    
    public List<ItemReturnPurchaseDraftEntity> getItemReturnPurchaseDraftList(ReturnPurchaseDraftEntity returnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemReturnPurchaseDraftEntity.class);
        c.add(Restrictions.eq("returnpurchasedraft", returnpurchasedraft));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}
