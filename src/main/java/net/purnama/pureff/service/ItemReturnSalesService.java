/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemReturnSalesDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("itemreturnsalesService")
public class ItemReturnSalesService {
    
    @Autowired
    ItemReturnSalesDao itemreturnsalesDao;
    
    @Transactional
    public void addItemReturnSales(ItemReturnSalesEntity itemreturnsales) {
        itemreturnsalesDao.addItemReturnSales(itemreturnsales);
    }
    
    @Transactional
    public List<ItemReturnSalesEntity> getItemReturnSalesList(ReturnSalesEntity returnsales) {
        return itemreturnsalesDao.getItemReturnSalesList(returnsales);
    }
    
    @Transactional
    public List<ItemReturnSalesEntity>
         getItemReturnSalesList(Calendar start, Calendar end, WarehouseEntity warehouse, 
                 PartnerEntity partner, 
                 CurrencyEntity currency, boolean status){
             return itemreturnsalesDao.getItemReturnSalesList(start, end, warehouse, partner, 
                     currency, status);
         }
}