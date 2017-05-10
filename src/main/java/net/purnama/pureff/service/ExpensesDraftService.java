/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ExpensesDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.transactional.draft.ExpensesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("expensesdraftService")
public class ExpensesDraftService {
    
    @Autowired
    ExpensesDraftDao expensesdraftDao;

    @Transactional
    public List<ExpensesDraftEntity> getExpensesDraftList() {
            return expensesdraftDao.getExpensesDraftList();
    }

    @Transactional
    public ExpensesDraftEntity getExpensesDraft(String id) {
            return expensesdraftDao.getExpensesDraft(id);
    }

    @Transactional
    public void addExpensesDraft(ExpensesDraftEntity expensesdraft) {
            expensesdraftDao.addExpensesDraft(expensesdraft);
    }

    @Transactional
    public void updateExpensesDraft(ExpensesDraftEntity expensesdraft) {
            expensesdraftDao.updateExpensesDraft(expensesdraft);
    }

    @Transactional
    public void deleteExpensesDraft(String id) {
            expensesdraftDao.deleteExpensesDraft(id);
    }
    
    @Transactional
    public List getExpensesDraftList(int itemperpage, int page, String sort, String keyword, UserEntity user){
        return expensesdraftDao.getExpensesDraftList(itemperpage, page, sort, keyword, user);
    }
    
    @Transactional
    public int countExpensesDraftList(String keyword, UserEntity user){
        return expensesdraftDao.countExpensesDraftList(keyword, user);
    }
}
