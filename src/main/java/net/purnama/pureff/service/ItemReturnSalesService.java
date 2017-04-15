/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ItemReturnSalesDao;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import net.purnama.pureff.entity.transactional.ItemReturnSalesEntity;
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
}