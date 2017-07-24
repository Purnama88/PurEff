/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
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
public class DeliveryDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<DeliveryEntity> getDeliveryList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(DeliveryEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
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
    
    public List getDeliveryList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(DeliveryEntity.class);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("destination", "%"+keyword+"%"));
        
        c.add(disjunction);

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
    
    public int countDeliveryList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(DeliveryEntity.class);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("destination", "%"+keyword+"%"));
        
        c.add(disjunction);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
    public List getDeliveryList(Calendar begin, Calendar end,
            WarehouseEntity warehouse, boolean status){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(DeliveryEntity.class);
        c.add(Restrictions.between("date", begin, end));
        if(warehouse != null){
            c.add(Restrictions.eq("warehouse", warehouse));
        }
        c.add(Restrictions.eq("status", status));
        c.addOrder(Order.asc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List ls = c.list();
        return ls;
    }
}
