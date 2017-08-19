/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.RoleEntity;
import net.purnama.pureff.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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
public class UserDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public UserEntity getUser(String username, String password){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UserEntity.class);
        c.add(Restrictions.eq("username", username));
        c.add(Restrictions.eq("password", password));
        return (UserEntity)c.uniqueResult();
    }
    
    public List<UserEntity> getActiveUserList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("username"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<UserEntity> getActiveUserList(RoleEntity role) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.add(Restrictions.eq("role", role));
        c.add(Restrictions.eq("status", true));
        c.addOrder(Order.asc("username"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public List<UserEntity> getUserList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RoleEntity.class);
        c.addOrder(Order.asc("username"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public UserEntity getUser(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        UserEntity p = (UserEntity) session.get(UserEntity.class, id);
        return p;
    }
    
    public UserEntity getUserByUsername(String username){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UserEntity.class);
        c.add(Restrictions.eq("username", username));
        return (UserEntity)c.uniqueResult();
    }
    
    public UserEntity addUser(UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }
    
    public void updateUser(UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
    }
    
    public List getUserList(
            int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UserEntity.class);
        Criterion username = Restrictions.like("username", "%"+keyword+"%");
        Criterion name = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(username,name);
        c.add(orExp);
        
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
    
    public int countUserList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UserEntity.class);
        Criterion username = Restrictions.like("username", "%"+keyword+"%");
        Criterion name = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(username,name);
        c.add(orExp);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}