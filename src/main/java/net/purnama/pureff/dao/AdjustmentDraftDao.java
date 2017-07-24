/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
        Criteria c = session.createCriteria(AdjustmentDraftEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
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
    
    public List getAdjustmentDraftList(int itemperpage, int page, String sort, 
            String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AdjustmentDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.like("lastmodifiedby", user));

        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countAdjustmentDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AdjustmentDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.like("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
