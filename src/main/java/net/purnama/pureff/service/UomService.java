/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.UomDao;
import net.purnama.pureff.entity.UomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("uomService")
public class UomService {
    
    @Autowired
    UomDao uomDao;

    @Transactional
    public List<UomEntity> getParentUomList() {
            return uomDao.getParentUomList();
    }
    
    @Transactional
    public List<UomEntity> getActiveParentUomList() {
            return uomDao.getActiveParentUomList();
    }
    
    @Transactional
    public List<UomEntity> getUomList(UomEntity parent) {
            return uomDao.getUomList(parent);
    }
    
    @Transactional
    public List<UomEntity> getActiveUomList() {
            return uomDao.getActiveUomList();
    }
    
    @Transactional
    public List<UomEntity> getUomList() {
            return uomDao.getUomList();
    }

    @Transactional
    public UomEntity getUom(String id) {
            return uomDao.getUom(id);
    }
    
    @Transactional
    public UomEntity getUomByName(String name) {
            return uomDao.getUomByName(name);
    }

    @Transactional
    public void addUom(UomEntity uom) {
            uomDao.addUom(uom);
    }

    @Transactional
    public void updateUom(UomEntity uom) {
            uomDao.updateUom(uom);
    }
    
    @Transactional
    public List getUomList(int itemperpage, int page, String sort, String keyword, UomEntity parent){
        return uomDao.getUomList(itemperpage, page, sort, keyword, parent);
    }
    
    @Transactional
    public int countUomList(String keyword, UomEntity parent){
        return uomDao.countUomList(keyword, parent);
    }
}
