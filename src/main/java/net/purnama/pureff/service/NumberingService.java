/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.NumberingDao;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("numberingService")
public class NumberingService {
    
    @Autowired
    NumberingDao numberingDao;

    @Transactional
    public List<NumberingEntity> getNumberingList() {
            return numberingDao.getNumberingList();
    }

    @Transactional
    public List<NumberingEntity> getNumberingList(WarehouseEntity warehouse, MenuEntity menu) {
            return numberingDao.getNumberingList(warehouse, menu);
    }
    
    @Transactional
    public NumberingEntity getNumbering(String id) {
            return numberingDao.getNumbering(id);
    }
    
    @Transactional
    public NumberingEntity getDefaultNumbering(MenuEntity menu, WarehouseEntity warehouse){
            return numberingDao.getDefaultNumbering(menu, warehouse);
    }

    @Transactional
    public void addNumbering(NumberingEntity numbering) {
            numberingDao.addNumbering(numbering);
    }

    @Transactional
    public void updateNumbering(NumberingEntity numbering) {
            numberingDao.updateNumbering(numbering);
    }
}
