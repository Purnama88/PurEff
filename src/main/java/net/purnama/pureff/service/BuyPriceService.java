/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.BuyPriceDao;
import net.purnama.pureff.entity.BuyPriceEntity;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.UomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("buypriceService")
public class BuyPriceService {
    
    @Autowired
    BuyPriceDao buypriceDao;

    @Transactional
    public List<BuyPriceEntity> getBuyPriceList() {
            return buypriceDao.getBuyPriceList();
    }

    @Transactional
    public List<BuyPriceEntity> getBuyPriceList(ItemEntity item) {
            return buypriceDao.getBuyPriceList(item);
    }
    
    @Transactional
    public BuyPriceEntity getBuyPrice(ItemEntity item, UomEntity uom) {
            return buypriceDao.getBuyPrice(item, uom);
    }
    
    @Transactional
    public BuyPriceEntity getBuyPrice(String id) {
            return buypriceDao.getBuyPrice(id);
    }

    @Transactional
    public void addBuyPrice(BuyPriceEntity buyprice) {
            buypriceDao.addBuyPrice(buyprice);
    }

    @Transactional
    public void updateBuyPrice(BuyPriceEntity buyprice) {
            buypriceDao.updateBuyPrice(buyprice);
    }
    
    @Transactional
    public void deleteBuyPrice(String id) {
            buypriceDao.deleteBuyPrice(id);
    }
}