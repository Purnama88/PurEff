/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.SellPriceDao;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.SellPriceEntity;
import net.purnama.pureff.entity.UomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("sellpriceService")
public class SellPriceService {
    
    @Autowired
    SellPriceDao sellpriceDao;

    @Transactional
    public List<SellPriceEntity> getSellPriceList() {
            return sellpriceDao.getSellPriceList();
    }

    @Transactional
    public List<SellPriceEntity> getSellPriceList(ItemEntity item) {
            return sellpriceDao.getSellPriceList(item);
    }
    
    @Transactional
    public List<SellPriceEntity> getSellPriceList(UomEntity uom) {
            return sellpriceDao.getSellPriceList(uom);
    }
    
    @Transactional
    public SellPriceEntity getSellPrice(ItemEntity item, UomEntity uom) {
            return sellpriceDao.getSellPrice(item, uom);
    }
    
    @Transactional
    public SellPriceEntity getSellPrice(String id) {
            return sellpriceDao.getSellPrice(id);
    }

    @Transactional
    public void addSellPrice(SellPriceEntity sellprice) {
            sellpriceDao.addSellPrice(sellprice);
    }

    @Transactional
    public void updateSellPrice(SellPriceEntity sellprice) {
            sellpriceDao.updateSellPrice(sellprice);
    }
    
    @Transactional
    public void deleteSellPrice(String id) {
            sellpriceDao.deleteSellPrice(id);
    }
}