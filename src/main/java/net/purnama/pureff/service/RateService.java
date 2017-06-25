/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.RateDao;
import net.purnama.pureff.entity.CurrencyEntity;
import net.purnama.pureff.entity.RateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("rateService")
public class RateService {
    
    @Autowired
    RateDao rateDao;

    @Transactional
    public RateEntity getLastRate(CurrencyEntity currency) {
            return rateDao.getLastRate(currency);
    }
    
    @Transactional
    public List<RateEntity> getRateList() {
            return rateDao.getRateList();
    }

    @Transactional
    public RateEntity getRate(String id) {
            return rateDao.getRate(id);
    }

    @Transactional
    public void addRate(RateEntity rate) {
            rateDao.addRate(rate);
    }

    @Transactional
    public void updateRate(RateEntity rate) {
            rateDao.updateRate(rate);
    }
    
    @Transactional
    public List getRateList(CurrencyEntity currency, int itemperpage, int page, String sort, String keyword){
        return rateDao.getRateList(currency, itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countRateList(CurrencyEntity currency, String keyword){
        return rateDao.countRateList(currency, keyword);
    }
}