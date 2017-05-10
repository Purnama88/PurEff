/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.DeliveryDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("deliverydraftService")
public class DeliveryDraftService {
    
    @Autowired
    DeliveryDraftDao deliverydraftDao;

    @Transactional
    public List<DeliveryDraftEntity> getDeliveryDraftList() {
            return deliverydraftDao.getDeliveryDraftList();
    }

    @Transactional
    public DeliveryDraftEntity getDeliveryDraft(String id) {
            return deliverydraftDao.getDeliveryDraft(id);
    }

    @Transactional
    public void addDeliveryDraft(DeliveryDraftEntity deliverydraft) {
            deliverydraftDao.addDeliveryDraft(deliverydraft);
    }

    @Transactional
    public void updateDeliveryDraft(DeliveryDraftEntity deliverydraft) {
            deliverydraftDao.updateDeliveryDraft(deliverydraft);
    }

    @Transactional
    public void deleteDeliveryDraft(String id) {
            deliverydraftDao.deleteDeliveryDraft(id);
    }
    
    @Transactional
    public List getDeliveryDraftList(int itemperpage, int page, String sort, 
            String keyword, UserEntity user){
        return deliverydraftDao.getDeliveryDraftList(itemperpage, page, sort, keyword, user);
    }
    
    @Transactional
    public int countDeliveryDraftList(String keyword, UserEntity user){
        return deliverydraftDao.countDeliveryDraftList(keyword, user);
    }
}