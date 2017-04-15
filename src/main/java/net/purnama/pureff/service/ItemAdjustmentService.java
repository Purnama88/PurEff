/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemAdjustmentDao;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemadjustmentService")
public class ItemAdjustmentService {
    
    @Autowired
    ItemAdjustmentDao itemadjustmentDao;
    
    @Transactional
    public List<ItemAdjustmentEntity> getItemAdjustmentList(AdjustmentEntity adjustment) {
        return itemadjustmentDao.getItemAdjustmentList(adjustment);
    }
}
