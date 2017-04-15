/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ReturnSalesDao;
import net.purnama.pureff.entity.transactional.ReturnSalesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("returnsalesService")
public class ReturnSalesService {
    
    @Autowired
    ReturnSalesDao returnsalesDao;

    @Transactional
    public List<ReturnSalesEntity> getReturnSalesList() {
            return returnsalesDao.getReturnSalesList();
    }

    @Transactional
    public ReturnSalesEntity getReturnSales(String id) {
            return returnsalesDao.getReturnSales(id);
    }

    @Transactional
    public void addReturnSales(ReturnSalesEntity returnsales) {
            returnsalesDao.addReturnSales(returnsales);
    }

    @Transactional
    public void updateReturnSales(ReturnSalesEntity returnsales) {
            returnsalesDao.updateReturnSales(returnsales);
    }
}