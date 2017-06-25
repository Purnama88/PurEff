/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.Calendar;
import java.util.List;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    
    public List getReturnSalesList(int itemperpage, int page, String sort, String keyword){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesEntity.class);
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        
        if(sort.contains("-")){
            c.addOrder(Order.desc(sort.substring(1)));
        }
        else{
            c.addOrder(Order.asc(sort));
        }
        
        c.setFirstResult(itemperpage * (page-1));
        c.setMaxResults(itemperpage);
        
        List ls = c.list();
        return ls;
    }
    
    public int countReturnSalesList(String keyword) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesEntity.class);

        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.like("number", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("warehouse_code", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("partner_name", "%"+keyword+"%"));
        disjunction.add(Restrictions.like("currency_code", "%"+keyword+"%"));
        
        c.add(disjunction);
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
    
    public List getUnpaidReturnSalesList(PartnerEntity partner,
            CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesEntity.class);
        c.add(Restrictions.eq("partner", partner));
        c.add(Restrictions.eq("currency", currency));
        c.add(Restrictions.eq("status", true));
        c.add(Restrictions.gt("remaining", 0.0));
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
    
    public List getReturnSalesList(Calendar begin, Calendar end,
            WarehouseEntity warehouse, PartnerEntity partner, CurrencyEntity currency){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesEntity.class);
        c.add(Restrictions.between("date", begin, end));
        if(partner != null){
            c.add(Restrictions.eq("partner", partner));
        }
        if(warehouse != null){
            c.add(Restrictions.eq("warehouse", warehouse));
        }
        if(currency != null){
            c.add(Restrictions.eq("currency", currency));
        }
        c.addOrder(Order.asc("date"));
        List ls = c.list();
        return ls;
    }
}
