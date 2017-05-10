/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemDeliveryDao;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemdeliveryService")
public class ItemDeliveryService {
    
    @Autowired
    ItemDeliveryDao itemdeliveryDao;
    
    @Transactional
    public List<ItemDeliveryEntity> getItemDeliveryList(DeliveryEntity delivery) {
        return itemdeliveryDao.getItemDeliveryList(delivery);
    }
    
    @Transactional
    public void addItemDelivery(ItemDeliveryEntity itemdelivery){
        itemdeliveryDao.addItemDelivery(itemdelivery);
    }
}
