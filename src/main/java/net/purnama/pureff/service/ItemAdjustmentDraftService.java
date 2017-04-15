/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemAdjustmentDraftDao;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemAdjustmentDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemadjustmentdraftService")
public class ItemAdjustmentDraftService {
    
    @Autowired
    ItemAdjustmentDraftDao itemadjustmentdraftDao;
    
    @Transactional
    public List<ItemAdjustmentDraftEntity> getItemAdjustmentDraftList(AdjustmentDraftEntity adjustmentdraft) {
        return itemadjustmentdraftDao.getItemAdjustmentDraftList(adjustmentdraft);
    }
}