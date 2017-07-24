/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ReturnPurchaseDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
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
    
    @Transactional
    public List getReturnPurchaseList(int itemperpage, int page, String sort, String keyword){
        return returnpurchaseDao.getReturnPurchaseList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countReturnPurchaseList(String keyword){
        return returnpurchaseDao.countReturnPurchaseList(keyword);
    }
    
    @Transactional
    public List getUnpaidReturnPurchaseList(PartnerEntity partner,
            CurrencyEntity currency){
        return returnpurchaseDao.getUnpaidReturnPurchaseList(partner, currency);
    }
    
    @Transactional
    public List getReturnPurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse,
            PartnerEntity partner, CurrencyEntity currency, boolean status){
        return returnpurchaseDao.getReturnPurchaseList(start, end, warehouse, partner, currency, status);
    }
}
