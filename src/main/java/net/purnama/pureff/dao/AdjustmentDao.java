/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class AdjustmentDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<AdjustmentEntity> getAdjustmentList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<AdjustmentEntity> ls = session.createQuery("from AdjustmentEntity").list();
        return ls;
    }
    
    public AdjustmentEntity getAdjustment(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        AdjustmentEntity p = (AdjustmentEntity) session.get(AdjustmentEntity.class, id);
        return p;
    }
    
    public AdjustmentEntity addAdjustment(AdjustmentEntity adjustment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(adjustment);
        return adjustment;
    }

    public void updateAdjustment(AdjustmentEntity adjustment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(adjustment);
    }
}
