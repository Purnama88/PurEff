/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
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
public class ReturnSalesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public List<ReturnSalesDraftEntity> getReturnSalesDraftList() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesDraftEntity.class);
        c.addOrder(Order.desc("date"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
    
    public ReturnSalesDraftEntity getReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnSalesDraftEntity p = (ReturnSalesDraftEntity) session.get(ReturnSalesDraftEntity.class, id);
        return p;
    }
    
    public ReturnSalesDraftEntity addReturnSalesDraft(ReturnSalesDraftEntity returnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(returnsalesdraft);
        return returnsalesdraft;
    }

    public void updateReturnSalesDraft(ReturnSalesDraftEntity returnsalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(returnsalesdraft);
    }
    
    public void deleteReturnSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ReturnSalesDraftEntity p = getReturnSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public List getReturnSalesDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        
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
    
    public int countReturnSalesDraftList(String keyword, UserEntity user) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ReturnSalesDraftEntity.class);
        c.add(Restrictions.like("id", "%"+keyword+"%"));
        c.add(Restrictions.eq("lastmodifiedby", user));
        c.setProjection(Projections.rowCount());
        List result = c.list();
        int resultint = Integer.valueOf(result.get(0).toString());
        
        return resultint;
    }
}
