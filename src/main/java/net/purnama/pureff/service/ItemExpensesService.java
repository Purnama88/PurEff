/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemExpensesDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ExpensesEntity;
import net.purnama.pureff.entity.transactional.ItemExpensesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemexpensesService")
public class ItemExpensesService {
    
    @Autowired
    ItemExpensesDao itemexpensesDao;
    
    @Transactional
    public void addItemExpenses(ItemExpensesEntity itemexpenses) {
        itemexpensesDao.addItemExpenses(itemexpenses);
    }
    
    @Transactional
    public List<ItemExpensesEntity> getItemExpensesList(ExpensesEntity expenses) {
        return itemexpensesDao.getItemExpensesList(expenses);
    }
    
    @Transactional
    public List<ItemExpensesEntity>
    getItemExpensesList(Calendar start, Calendar end, WarehouseEntity warehouse,
             PartnerEntity partner,
             CurrencyEntity currency, boolean status){
        return itemexpensesDao.getItemExpensesList(start, end, warehouse, partner, currency, status);
    }
}
