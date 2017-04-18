/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.PartnerTypeEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */

@Repository
public class PartnerTypeDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PartnerTypeEntity> getActivePartnerTypeList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerTypeEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<PartnerTypeEntity> getPartnerTypeList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PartnerTypeEntity> ls = session.createQuery("from PartnerTypeEntity").list();
        return ls;
    }
    
    public PartnerTypeEntity getPartnerType(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PartnerTypeEntity p = (PartnerTypeEntity) session.get(PartnerTypeEntity.class, id);
        return p;
    }
    
    public PartnerTypeEntity addPartnerType(PartnerTypeEntity partnertype) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(partnertype);
        return partnertype;
    }

    public void updatePartnerType(PartnerTypeEntity partnertype) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(partnertype);
    }
    
    public List getPartnerTypeList(int itemperpage, int page, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerTypeEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        return c.list();
    }
    
    public int countPartnerTypeList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerTypeEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
