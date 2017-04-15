/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class AdjustmentDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<AdjustmentDraftEntity> getAdjustmentDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<AdjustmentDraftEntity> ls = session.createQuery("from AdjustmentDraftEntity").list();
        return ls;
    }
    
    public AdjustmentDraftEntity getAdjustmentDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        AdjustmentDraftEntity p = (AdjustmentDraftEntity) session.get(AdjustmentDraftEntity.class, id);
        return p;
    }
    
    public AdjustmentDraftEntity addAdjustmentDraft(AdjustmentDraftEntity adjustmentdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(adjustmentdraft);
        return adjustmentdraft;
    }

    public void updateAdjustmentDraft(AdjustmentDraftEntity adjustmentdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(adjustmentdraft);
    }
    
    public void deleteAdjustmentDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        AdjustmentDraftEntity p = getAdjustmentDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
}
