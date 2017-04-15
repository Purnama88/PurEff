/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ReturnPurchaseDao;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("returnpurchaseService")
public class ReturnPurchaseService {
    
    @Autowired
    ReturnPurchaseDao returnpurchaseDao;

    @Transactional
    public List<ReturnPurchaseEntity> getReturnPurchaseList() {
            return returnpurchaseDao.getReturnPurchaseList();
    }

    @Transactional
    public ReturnPurchaseEntity getReturnPurchase(String id) {
            return returnpurchaseDao.getReturnPurchase(id);
    }

    @Transactional
    public void addReturnPurchase(ReturnPurchaseEntity returnpurchase) {
            returnpurchaseDao.addReturnPurchase(returnpurchase);
    }

    @Transactional
    public void updateReturnPurchase(ReturnPurchaseEntity returnpurchase) {
            returnpurchaseDao.updateReturnPurchase(returnpurchase);
    }
}
