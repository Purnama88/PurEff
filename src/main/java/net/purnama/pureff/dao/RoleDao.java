/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.RoleEntity;
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
public class RoleDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<RoleEntity> getActiveRoleList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("name"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<RoleEntity> getRoleList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.addOrder(Order.asc("name"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public RoleEntity getRole(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        RoleEntity p = (RoleEntity) session.get(RoleEntity.class, id);
        return p;
    }
    
    public RoleEntity getRoleByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.eq("name", name));
        return (RoleEntity)c.uniqueResult();
    }
    
    public RoleEntity addRole(RoleEntity role) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(role);
        return role;
    }

    public void updateRole(RoleEntity role) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(role);
    }
    
    public List getRoleList(
            int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));

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
    
    public int countRoleList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.like("name", "%"+keyword+"%"));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}