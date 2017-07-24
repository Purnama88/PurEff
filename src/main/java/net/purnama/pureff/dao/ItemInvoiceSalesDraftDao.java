/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.dao;

import java.util.List;
import net.purnama.pureff.entity.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemInvoiceSalesDraftEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Purnama
 */
@Repository
public class ItemInvoiceSalesDraftDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addItemInvoiceSalesDraft(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(iteminvoicesalesdraft);
    }
    
    public void updateItemInvoiceSalesDraft(ItemInvoiceSalesDraftEntity iteminvoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(iteminvoicesalesdraft);
    }
    
    public void deleteItemInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceSalesDraftEntity p = getItemInvoiceSalesDraft(id);
        if (null != p) {
                session.delete(p);
        }
    }
    
    public ItemInvoiceSalesDraftEntity getItemInvoiceSalesDraft(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        ItemInvoiceSalesDraftEntity p = (ItemInvoiceSalesDraftEntity) 
                session.get(ItemInvoiceSalesDraftEntity.class, id);
        return p;
    }
    
    public List<ItemInvoiceSalesDraftEntity> getItemInvoiceSalesDraftList(InvoiceSalesDraftEntity invoicesalesdraft) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(ItemInvoiceSalesDraftEntity.class);
        c.add(Restrictions.eq("invoicesalesdraft", invoicesalesdraft));
        c.addOrder(Order.asc("id"));
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return c.list();
    }
}