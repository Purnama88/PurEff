/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemReturnPurchaseDao;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ReturnPurchaseEntity;
import net.purnama.pureff.entity.transactional.ItemReturnPurchaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemreturnpurchaseService")
public class ItemReturnPurchaseService {
    
    @Autowired
    ItemReturnPurchaseDao itemreturnpurchaseDao;
    
    @Transactional
    public void addItemReturnPurchase(ItemReturnPurchaseEntity itemreturnpurchase) {
        itemreturnpurchaseDao.addItemReturnPurchase(itemreturnpurchase);
    }
    
    @Transactional
    public List<ItemReturnPurchaseEntity> getItemReturnPurchaseList(ReturnPurchaseEntity returnpurchase) {
        return itemreturnpurchaseDao.getItemReturnPurchaseList(returnpurchase);
    }
    
    @Transactional
    public List<ItemReturnPurchaseEntity>
         getItemReturnPurchaseList(Calendar start, Calendar end, WarehouseEntity warehouse, boolean status){
             return itemreturnpurchaseDao.getItemReturnPurchaseList(start, end, warehouse, status);
         }
}
