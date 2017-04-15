/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ReturnPurchaseDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnPurchaseEntity> getReturnPurchaseList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReturnPurchaseEntity> ls = session.createQuery("from ReturnPurchaseEntity").list();
        return ls;
    }
    
    public ReturnPurchaseEntity getReturnPurchase(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnPurchaseEntity p = (ReturnPurchaseEntity) session.get(ReturnPurchaseEntity.class, id);
        return p;
    }
    
    public ReturnPurchaseEntity addReturnPurchase(ReturnPurchaseEntity returnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnpurchase);
        return returnpurchase;
    }

    public void updateReturnPurchase(ReturnPurchaseEntity returnpurchase) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnpurchase);
    }
}