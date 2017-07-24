/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.DeliveryDao;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("deliveryService")
public class DeliveryService {
    
    @Autowired
    DeliveryDao deliveryDao;

    @Transactional
    public List<DeliveryEntity> getDeliveryList() {
            return deliveryDao.getDeliveryList();
    }

    @Transactional
    public DeliveryEntity getDelivery(String id) {
            return deliveryDao.getDelivery(id);
    }

    @Transactional
    public void addDelivery(DeliveryEntity delivery) {
            deliveryDao.addDelivery(delivery);
    }

    @Transactional
    public void updateDelivery(DeliveryEntity delivery) {
            deliveryDao.updateDelivery(delivery);
    }
    
    @Transactional
    public List getDeliveryList(int itemperpage, int page, String sort, String keyword){
        return deliveryDao.getDeliveryList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countDeliveryList(String keyword){
        return deliveryDao.countDeliveryList(keyword);
    }
    
    @Transactional
    public List getDeliveryList(Calendar start, Calendar end, WarehouseEntity warehouse, boolean status){
        return deliveryDao.getDeliveryList(start, end, warehouse, status);
    }
}