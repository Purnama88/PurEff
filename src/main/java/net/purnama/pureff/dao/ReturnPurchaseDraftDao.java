/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ReturnPurchaseDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnPurchaseDraftEntity> getReturnPurchaseDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReturnPurchaseDraftEntity> ls = session.createQuery("from ReturnPurchaseDraftEntity").list();
        return ls;
    }
    
    public ReturnPurchaseDraftEntity getReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseDraftEntity p = (ReturnPurchaseDraftEntity) session.get(ReturnPurchaseDraftEntity.class, id);
        return p;
    }
    
    public ReturnPurchaseDraftEntity addReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnpurchasedraft);
        return returnpurchasedraft;
    }

    public void updateReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnpurchasedraft);
    }
    
    public void deleteReturnPurchaseDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseDraftEntity p = getReturnPurchaseDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}

