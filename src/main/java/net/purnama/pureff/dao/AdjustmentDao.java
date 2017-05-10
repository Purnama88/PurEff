/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
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
    
    public List getAdjustmentList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AdjustmentEntity.class);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        
        c.add(disjunction);

        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countAdjustmentList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(AdjustmentEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
