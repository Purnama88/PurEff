/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemGroupDao;
import net.purnama.pureff.entity.ItemGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemgroupService")
public class ItemGroupService {
    
    @Autowired
    ItemGroupDao itemgroupDao;

    @Transactional
    public List<ItemGroupEntity> getActiveItemGroupList() {
            return itemgroupDao.getActiveItemGroupList();
    }
    
    @Transactional
    public List<ItemGroupEntity> getItemGroupList() {
            return itemgroupDao.getItemGroupList();
    }

    @Transactional
    public ItemGroupEntity getItemGroup(String id) {
            return itemgroupDao.getItemGroup(id);
    }

    @Transactional
    public void addItemGroup(ItemGroupEntity itemgroup) {
            itemgroupDao.addItemGroup(itemgroup);
    }

    @Transactional
    public void updateItemGroup(ItemGroupEntity itemgroup) {
            itemgroupDao.updateItemGroup(itemgroup);
    }

    @Transactional
    public void deleteItemGroup(String id) {
            itemgroupDao.deleteItemGroup(id);
    }
}
