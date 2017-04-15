/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class DeliveryDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<DeliveryDraftEntity> getDeliveryDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<DeliveryDraftEntity> ls = session.createQuery("from DeliveryDraftEntity").list();
        return ls;
    }
    
    public DeliveryDraftEntity getDeliveryDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        DeliveryDraftEntity p = (DeliveryDraftEntity) session.get(DeliveryDraftEntity.class, id);
        return p;
    }
    
    public DeliveryDraftEntity addDeliveryDraft(DeliveryDraftEntity deliverydraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(deliverydraft);
        return deliverydraft;
    }

    public void updateDeliveryDraft(DeliveryDraftEntity deliverydraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(deliverydraft);
    }
    
    public void deleteDeliveryDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        DeliveryDraftEntity p = getDeliveryDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
