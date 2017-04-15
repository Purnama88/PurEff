/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.NumberingNameEntity;
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
public class NumberingNameDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<NumberingNameEntity> getActiveNumberingNameList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(NumberingNameEntity.class);
        c.add(Restrictions.eq("status", true));
        return c.list();
    }
    
    public List<NumberingNameEntity> getNumberingNameList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<NumberingNameEntity> ls = session.createQuery("from NumberingNameEntity").list();
        return ls;
    }
    
    public NumberingNameEntity getNumberingName(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        NumberingNameEntity p = (NumberingNameEntity) session.get(NumberingNameEntity.class, id);
        return p;
    }
    
    public NumberingNameEntity addNumberingName(NumberingNameEntity numberingname) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(numberingname);
        return numberingname;
    }

    public void updateNumberingName(NumberingNameEntity numberingname) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(numberingname);
    }
}
