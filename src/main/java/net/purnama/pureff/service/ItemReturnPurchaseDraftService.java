/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemReturnPurchaseDraftDao;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnPurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemreturnpurchasedraftService")
public class ItemReturnPurchaseDraftService {
    
    @Autowired
    ItemReturnPurchaseDraftDao itemreturnpurchasedraftDao;
    
    @Transactional
    public void addItemReturnPurchaseDraft(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft) {
        itemreturnpurchasedraftDao.addItemReturnPurchaseDraft(itemreturnpurchasedraft);
    }
    
    @Transactional
    public void updateItemReturnPurchaseDraft(ItemReturnPurchaseDraftEntity itemreturnpurchasedraft) {
        itemreturnpurchasedraftDao.updateItemReturnPurchaseDraft(itemreturnpurchasedraft);
    }
    
    @Transactional
    public void deleteItemReturnPurchaseDraft(String id) {
        itemreturnpurchasedraftDao.deleteItemReturnPurchaseDraft(id);
    }
    
    @Transactional
    public ItemReturnPurchaseDraftEntity getItemReturnPurchaseDraft(String id) {
        return itemreturnpurchasedraftDao.getItemReturnPurchaseDraft(id);
    }
    
    @Transactional
    public List<ItemReturnPurchaseDraftEntity> getItemReturnPurchaseDraftList(ReturnPurchaseDraftEntity returnpurchasedraft) {
        return itemreturnpurchasedraftDao.getItemReturnPurchaseDraftList(returnpurchasedraft);
    }
}
