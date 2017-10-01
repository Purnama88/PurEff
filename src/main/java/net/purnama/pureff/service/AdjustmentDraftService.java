/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.AdjustmentDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("adjustmentdraftService")
public class AdjustmentDraftService {
    
    @Autowired
    AdjustmentDraftDao adjustmentdraftDao;

    @Transactional
    public List<AdjustmentDraftEntity> getAdjustmentDraftList() {
            return adjustmentdraftDao.getAdjustmentDraftList();
    }

    @Transactional
    public AdjustmentDraftEntity getAdjustmentDraft(String id) {
            return adjustmentdraftDao.getAdjustmentDraft(id);
    }

    @Transactional
    public void addAdjustmentDraft(AdjustmentDraftEntity adjustmentdraft) {
            adjustmentdraftDao.addAdjustmentDraft(adjustmentdraft);
    }

    @Transactional
    public void updateAdjustmentDraft(AdjustmentDraftEntity adjustmentdraft) {
            adjustmentdraftDao.updateAdjustmentDraft(adjustmentdraft);
    }

    @Transactional
    public void deleteAdjustmentDraft(String id) {
            adjustmentdraftDao.deleteAdjustmentDraft(id);
    }
    
    @Transactional
    public List getAdjustmentDraftList(int itemperpage, int page, String sort,
            String keyword, UserEntity user, WarehouseEntity warehouse){
        return adjustmentdraftDao.getAdjustmentDraftList(itemperpage, page, sort, keyword, user, warehouse);
    }
    
    @Transactional
    public int countAdjustmentDraftList(String keyword, UserEntity user, WarehouseEntity warehouse){
        return adjustmentdraftDao.countAdjustmentDraftList(keyword, user, warehouse);
    }
}