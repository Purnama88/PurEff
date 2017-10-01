/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ReturnPurchaseDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnPurchaseDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("returnpurchasedraftService")
public class ReturnPurchaseDraftService {
    
    @Autowired
    ReturnPurchaseDraftDao returnpurchasedraftDao;

    @Transactional
    public List<ReturnPurchaseDraftEntity> getReturnPurchaseDraftList() {
            return returnpurchasedraftDao.getReturnPurchaseDraftList();
    }

    @Transactional
    public ReturnPurchaseDraftEntity getReturnPurchaseDraft(String id) {
            return returnpurchasedraftDao.getReturnPurchaseDraft(id);
    }

    @Transactional
    public void addReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
            returnpurchasedraftDao.addReturnPurchaseDraft(returnpurchasedraft);
    }

    @Transactional
    public void updateReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
            returnpurchasedraftDao.updateReturnPurchaseDraft(returnpurchasedraft);
    }

    @Transactional
    public void deleteReturnPurchaseDraft(String id) {
            returnpurchasedraftDao.deleteReturnPurchaseDraft(id);
    }
    
    @Transactional
    public List getReturnPurchaseDraftList(int itemperpage, int page, String sort,
            String keyword, UserEntity user, WarehouseEntity warehouse){
        return returnpurchasedraftDao.getReturnPurchaseDraftList(itemperpage, page, sort, keyword, user, warehouse);
    }
    
    @Transactional
    public int countReturnPurchaseDraftList(String keyword, UserEntity user, WarehouseEntity warehouse){
        return returnpurchasedraftDao.countReturnPurchaseDraftList(keyword, user, warehouse);
    }
}