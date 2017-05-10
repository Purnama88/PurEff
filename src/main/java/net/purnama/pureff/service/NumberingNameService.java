/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.NumberingNameDao;
import net.purnama.pureff.entity.NumberingNameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("numberingnameService")
public class NumberingNameService {
    
    	@Autowired
	NumberingNameDao numberingnameDao;
	
        @Transactional
	public List<NumberingNameEntity> getActiveNumberingNameList() {
		return numberingnameDao.getActiveNumberingNameList();
	}
        
	@Transactional
	public List<NumberingNameEntity> getNumberingNameList() {
		return numberingnameDao.getNumberingNameList();
	}

	@Transactional
	public NumberingNameEntity getNumberingName(String id) {
		return numberingnameDao.getNumberingName(id);
	}
        
        @Transactional
	public NumberingNameEntity getNumberingNameByName(String name) {
		return numberingnameDao.getNumberingNameByName(name);
	}

	@Transactional
	public void addNumberingName(NumberingNameEntity numberingname) {
		numberingnameDao.addNumberingName(numberingname);
	}

	@Transactional
	public void updateNumberingName(NumberingNameEntity numberingname) {
		numberingnameDao.updateNumberingName(numberingname);
	}
        
        @Transactional
        public List getNumberingNameList(int itemperpage, int page, String sort, String keyword){
            return numberingnameDao.getNumberingNameList(itemperpage, page, sort, keyword);
        }
        
        @Transactional
        public int countNumberingNameList(String keyword) {
            return numberingnameDao.countNumberingNameList(keyword);
        }
}
