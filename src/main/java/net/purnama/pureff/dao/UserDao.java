/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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
    
    public List<UserEntity> getUserList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserEntity> ls = session.createQuery("from UserEntity").list();
        return ls;
    }
    
    public UserEntity getUser(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        UserEntity p = (UserEntity) session.get(UserEntity.class, id);
        return p;
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
            //String sort, String order,
            int itemperpage, int page, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(UserEntity.class);
        Criterion username = Restrictions.like("username", "%"+keyword+"%");
        Criterion name = Restrictions.like("name", "%"+keyword+"%");
        LogicalExpression orExp = Restrictions.or(username,name);
        c.add(orExp);
//        if(order.equals(GlobalFields.PROPERTIES.getProperty("LABEL_ASCENDING"))){
//            c.addOrder(Order.asc(sort));
//        }
//        else{
//            c.addOrder(Order.desc(sort));
//        }

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