/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.PartnerEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class PartnerDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<PartnerEntity> getActivePartnerList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(PartnerEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<PartnerEntity> getPartnerList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PartnerEntity> ls = session.createQuery("from PartnerEntity").list();
        return ls;
    }
    
    public PartnerEntity getPartner(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        PartnerEntity p = (PartnerEntity) session.get(PartnerEntity.class, id);
        return p;
    }
    
    public PartnerEntity addPartner(PartnerEntity partner) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(partner);
        return partner;
    }

    public void updatePartner(PartnerEntity partner) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(partner);
    }
}
