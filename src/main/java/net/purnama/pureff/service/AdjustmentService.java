/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.AdjustmentDao;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("adjustmentService")
public class AdjustmentService {
    
    @Autowired
    AdjustmentDao adjustmentDao;

    @Transactional
    public List<AdjustmentEntity> getAdjustmentList() {
            return adjustmentDao.getAdjustmentList();
    }

    @Transactional
    public AdjustmentEntity getAdjustment(String id) {
            return adjustmentDao.getAdjustment(id);
    }

    @Transactional
    public void addAdjustment(AdjustmentEntity adjustment) {
            adjustmentDao.addAdjustment(adjustment);
    }

    @Transactional
    public void updateAdjustment(AdjustmentEntity adjustment) {
            adjustmentDao.updateAdjustment(adjustment);
    }
}