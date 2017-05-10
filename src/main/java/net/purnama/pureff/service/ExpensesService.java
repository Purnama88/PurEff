/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ExpensesDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("expensesService")
public class ExpensesService {
    
    @Autowired
    ExpensesDao expensesDao;

    @Transactional
    public List<ExpensesEntity> getExpensesList() {
            return expensesDao.getExpensesList();
    }

    @Transactional
    public ExpensesEntity getExpenses(String id) {
            return expensesDao.getExpenses(id);
    }

    @Transactional
    public void addExpenses(ExpensesEntity expenses) {
            expensesDao.addExpenses(expenses);
    }

    @Transactional
    public void updateExpenses(ExpensesEntity expenses) {
            expensesDao.updateExpenses(expenses);
    }
    
    @Transactional
    public List getExpensesList(int itemperpage, int page, String sort, String keyword){
        return expensesDao.getExpensesList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countExpensesList(String keyword){
        return expensesDao.countExpensesList(keyword);
    }
    
    @Transactional
    public List getUnpaidExpensesList(PartnerEntity partner,
            CurrencyEntity currency){
        return expensesDao.getUnpaidExpensesList(partner, currency);
    }
}
