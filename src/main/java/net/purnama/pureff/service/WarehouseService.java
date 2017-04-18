/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.WarehouseDao;
import net.purnama.pureff.entity.WarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("warehouseService")
public class WarehouseService {
    
    @Autowired
    WarehouseDao warehouseDao;

    @Transactional
    public List<WarehouseEntity> getWarehouse_IdCode_List() {
            return warehouseDao.getWarehouse_IdCode_List();
    }
    
    @Transactional
    public List<WarehouseEntity> getActiveWarehouseList() {
            return warehouseDao.getActiveWarehouseList();
    }
    
    @Transactional
    public List<WarehouseEntity> getWarehouseList() {
            return warehouseDao.getWarehouseList();
    }
    
    @Transactional
    public WarehouseEntity getWarehouse(String id) {
            return warehouseDao.getWarehouse(id);
    }
    
    @Transactional
    public void addWarehouse(WarehouseEntity warehouse) {
            warehouseDao.addWarehouse(warehouse);
    }

    @Transactional
    public void updateWarehouse(WarehouseEntity warehouse) {
            warehouseDao.updateWarehouse(warehouse);
    }
    
    @Transactional
    public List getWarehouseList(int itemperpage, int page, String keyword){
        return warehouseDao.getWarehouseList(itemperpage, page, keyword);
    }
    
    @Transactional
    public int countWarehouseList(String keyword){
        return warehouseDao.countWarehouseList(keyword);
    }
}
