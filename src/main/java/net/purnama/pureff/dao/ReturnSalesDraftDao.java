/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        List<ReturnSalesDraftEntity> ls = session.createQuery("from ReturnSalesDraftEntity").list();
        return ls;
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
}
