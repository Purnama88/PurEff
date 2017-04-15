/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.MenuEntity;
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
public class MenuDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<MenuEntity> getTransactionalMenuList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(MenuEntity.class);
        c.add(Restrictions.eq("transactional", true));
        return c.list();
    }
    
    public List<MenuEntity> getMenuList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<MenuEntity> ls = session.createQuery("from MenuEntity").list();
        return ls;
    }
    
    public MenuEntity getMenu(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        MenuEntity p = (MenuEntity) session.get(MenuEntity.class, id);
        return p;
    }
    
    public MenuEntity addMenu(MenuEntity menu) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(menu);
        return menu;
    }

    public void updateMenu(MenuEntity menu) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(menu);
    }
}
