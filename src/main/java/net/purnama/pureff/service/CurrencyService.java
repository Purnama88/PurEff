/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.CurrencyDao;
import net.purnama.pureff.entity.CurrencyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("currencyService")
public class CurrencyService {
    
    	@Autowired
	CurrencyDao currencyDao;
	
        @Transactional
	public CurrencyEntity getDefaultCurrency() {
		return currencyDao.getDefaultCurrency();
	}
        
	@Transactional
	public List<CurrencyEntity> getCurrencyList() {
		return currencyDao.getCurrencyList();
	}

        @Transactional
	public CurrencyEntity getCurrencyByCode(String code) {
		return currencyDao.getCurrencyByCode(code);
	}
        
	@Transactional
	public CurrencyEntity getCurrency(String id) {
		return currencyDao.getCurrency(id);
	}

	@Transactional
	public void addCurrency(CurrencyEntity currency) {
		currencyDao.addCurrency(currency);
	}

	@Transactional
	public void updateCurrency(CurrencyEntity currency) {
		currencyDao.updateCurrency(currency);
	}

}
