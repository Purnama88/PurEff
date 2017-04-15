/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class DeliveryDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<DeliveryEntity> getDeliveryList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<DeliveryEntity> ls = session.createQuery("from DeliveryEntity").list();
        return ls;
    }
    
    public DeliveryEntity getDelivery(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        DeliveryEntity p = (DeliveryEntity) session.get(DeliveryEntity.class, id);
        return p;
    }
    
    public DeliveryEntity addDelivery(DeliveryEntity delivery) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(delivery);
        return delivery;
    }

    public void updateDelivery(DeliveryEntity delivery) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(delivery);
    }
}
