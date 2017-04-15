/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemExpensesDraftDao;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemExpensesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemexpensesdraftService")
public class ItemExpensesDraftService {
    
    @Autowired
    ItemExpensesDraftDao itemexpensesdraftDao;
    
    @Transactional
    public void addItemExpensesDraft(ItemExpensesDraftEntity itemexpensesdraft) {
        itemexpensesdraftDao.addItemExpensesDraft(itemexpensesdraft);
    }
    
    @Transactional
    public void updateItemExpensesDraft(ItemExpensesDraftEntity itemexpensesdraft) {
        itemexpensesdraftDao.updateItemExpensesDraft(itemexpensesdraft);
    }
    
    @Transactional
    public void deleteItemExpensesDraft(String id) {
        itemexpensesdraftDao.deleteItemExpensesDraft(id);
    }
    
    @Transactional
    public ItemExpensesDraftEntity getItemExpensesDraft(String id) {
        return itemexpensesdraftDao.getItemExpensesDraft(id);
    }
    
    @Transactional
    public List<ItemExpensesDraftEntity> getItemExpensesDraftList(ExpensesDraftEntity expensesdraft) {
        return itemexpensesdraftDao.getItemExpensesDraftList(expensesdraft);
    }
}