/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemWarehouseDao;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemwarehouseService")
public class ItemWarehouseService {
    @Autowired
    ItemWarehouseDao itemwarehouseDao;

    @Transactional
    public List<ItemWarehouseEntity> getItemWarehouseList() {
            return itemwarehouseDao.getItemWarehouseList();
    }

    @Transactional
    public ItemWarehouseEntity getItemWarehouse(WarehouseEntity warehouse, ItemEntity item) {
            return itemwarehouseDao.getItemWarehouse(warehouse, item);
    }

    @Transactional
    public void addItemWarehouse(ItemWarehouseEntity itemwarehouse) {
            itemwarehouseDao.addItemWarehouse(itemwarehouse);
    }

    @Transactional
    public void updateItemWarehouse(ItemWarehouseEntity itemwarehouse) {
            itemwarehouseDao.updateItemWarehouse(itemwarehouse);
    }
    
    @Transactional
    public List getItemWarehouseList(WarehouseEntity warehouse, int itemperpage,
            int page, String sort, String keyword){
        return itemwarehouseDao.getItemWarehouseList(warehouse, itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countItemWarehouseList(WarehouseEntity warehouse, String keyword){
        return itemwarehouseDao.countItemWarehouseList(warehouse, keyword);
    }
  
    @Transactional
    public List getItemWarehouseList(WarehouseEntity warehouse, ItemGroupEntity itemgroup,
            boolean status){
        return itemwarehouseDao.getItemWarehouseList(warehouse, itemgroup, status);
    }
}
