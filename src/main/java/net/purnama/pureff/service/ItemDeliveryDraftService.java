/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemDeliveryDraftDao;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemDeliveryDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemdeliverydraftService")
public class ItemDeliveryDraftService {
    
    @Autowired
    ItemDeliveryDraftDao itemdeliverydraftDao;
    
    @Transactional
    public List<ItemDeliveryDraftEntity> getItemDeliveryDraftList(DeliveryDraftEntity deliverydraft) {
        return itemdeliverydraftDao.getItemDeliveryDraftList(deliverydraft);
    }
    
    @Transactional
    public ItemDeliveryDraftEntity getItemDeliveryDraft(String id){
        return itemdeliverydraftDao.getItemDeliveryDraft(id);
    }
    
    @Transactional
    public ItemDeliveryDraftEntity addItemDeliveryDraft(ItemDeliveryDraftEntity itemdeliverydraft){
        return itemdeliverydraftDao.addItemDeliveryDraft(itemdeliverydraft);
    }
    
    @Transactional
    public void updateItemDeliveryDraft(ItemDeliveryDraftEntity itemdeliverydraft){
        itemdeliverydraftDao.updateItemDeliveryDraft(itemdeliverydraft);
    }
    
    @Transactional
    public void deleteItemDeliveryDraft(String id){
        itemdeliverydraftDao.deleteItemDeliveryDraft(id);;
    }
}