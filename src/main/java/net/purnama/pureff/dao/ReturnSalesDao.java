/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ReturnSalesDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnSalesEntity> getReturnSalesList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReturnSalesEntity> ls = session.createQuery("from ReturnSalesEntity").list();
        return ls;
    }
    
    public ReturnSalesEntity getReturnSales(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnSalesEntity p = (ReturnSalesEntity) session.get(ReturnSalesEntity.class, id);
        return p;
    }
    
    public ReturnSalesEntity addReturnSales(ReturnSalesEntity returnsales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnsales);
        return returnsales;
    }

    public void updateReturnSales(ReturnSalesEntity returnsales) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnsales);
    }
}
