/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemReturnSalesDraftDao;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemReturnSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemreturnsalesdraftService")
public class ItemReturnSalesDraftService {
    
    @Autowired
    ItemReturnSalesDraftDao itemreturnsalesdraftDao;
    
    @Transactional
    public void addItemReturnSalesDraft(ItemReturnSalesDraftEntity itemreturnsalesdraft) {
        itemreturnsalesdraftDao.addItemReturnSalesDraft(itemreturnsalesdraft);
    }
    
    @Transactional
    public void updateItemReturnSalesDraft(ItemReturnSalesDraftEntity itemreturnsalesdraft) {
        itemreturnsalesdraftDao.updateItemReturnSalesDraft(itemreturnsalesdraft);
    }
    
    @Transactional
    public void deleteItemReturnSalesDraft(String id) {
        itemreturnsalesdraftDao.deleteItemReturnSalesDraft(id);
    }
    
    @Transactional
    public ItemReturnSalesDraftEntity getItemReturnSalesDraft(String id) {
        return itemreturnsalesdraftDao.getItemReturnSalesDraft(id);
    }
    
    @Transactional
    public List<ItemReturnSalesDraftEntity> getItemReturnSalesDraftList(ReturnSalesDraftEntity returnsalesdraft) {
        return itemreturnsalesdraftDao.getItemReturnSalesDraftList(returnsalesdraft);
    }
}
