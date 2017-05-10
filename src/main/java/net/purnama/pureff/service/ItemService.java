/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemDao;
import net.purnama.pureff.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemService")
public class ItemService {
    
    @Autowired
    ItemDao itemDao;

    @Transactional
    public List<ItemEntity> getActiveItemList() {
            return itemDao.getActiveItemList();
    }
    
    @Transactional
    public List<ItemEntity> getItemList() {
            return itemDao.getItemList();
    }

    @Transactional
    public ItemEntity getItem(String id) {
            return itemDao.getItem(id);
    }
    
    @Transactional
    public ItemEntity getItemByCode(String code) {
            return itemDao.getItemByCode(code);
    }

    @Transactional
    public void addItem(ItemEntity item) {
            itemDao.addItem(item);
    }

    @Transactional
    public void updateItem(ItemEntity item) {
            itemDao.updateItem(item);
    }
}