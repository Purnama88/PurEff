/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PartnerTypeDao;
import net.purnama.pureff.entity.PartnerTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("partnertypeService")
public class PartnerTypeService {
    @Autowired
    PartnerTypeDao partnertypeDao;

    @Transactional
    public List<PartnerTypeEntity> getActivePartnerTypeList() {
            return partnertypeDao.getActivePartnerTypeList();
    }
    
    @Transactional
    public List<PartnerTypeEntity> getPartnerTypeList() {
            return partnertypeDao.getPartnerTypeList();
    }

    @Transactional
    public PartnerTypeEntity getPartnerType(String id) {
            return partnertypeDao.getPartnerType(id);
    }

    @Transactional
    public void addPartnerType(PartnerTypeEntity partnertype) {
            partnertypeDao.addPartnerType(partnertype);
    }

    @Transactional
    public void updatePartnerType(PartnerTypeEntity partnertype) {
            partnertypeDao.updatePartnerType(partnertype);
    }
    
    @Transactional
    public List getPartnerTypeList(int itemperpage, int page, String keyword){
        return partnertypeDao.getPartnerTypeList(itemperpage, page, keyword);
    }
    
    @Transactional
    public int countPartnerTypeList(String keyword){
        return partnertypeDao.countPartnerTypeList(keyword);
    }
}
