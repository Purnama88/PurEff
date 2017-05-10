/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.PartnerDao;
import net.purnama.pureff.entity.PartnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("partnerService")
public class PartnerService {
    @Autowired
    PartnerDao partnerDao;

    @Transactional
    public List<PartnerEntity> getActivePartnerList() {
            return partnerDao.getActivePartnerList();
    }
    
    @Transactional
    public List<PartnerEntity> getPartnerList() {
            return partnerDao.getPartnerList();
    }

    @Transactional
    public PartnerEntity getPartner(String id) {
            return partnerDao.getPartner(id);
    }
    
    @Transactional
    public PartnerEntity getPartnerByCode(String code) {
            return partnerDao.getPartnerByCode(code);
    }

    @Transactional
    public void addPartner(PartnerEntity partner) {
            partnerDao.addPartner(partner);
    }

    @Transactional
    public void updatePartner(PartnerEntity partner) {
            partnerDao.updatePartner(partner);
    }
    
    @Transactional
    public List getPartnerList(int itemperpage, int page, String sort, String keyword){
        return partnerDao.getPartnerList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countPartnerList(String keyword){
        return partnerDao.countPartnerList(keyword);
    }
    
    @Transactional
    public List getCustomerList(){
        return partnerDao.getCustomerList();
    }
    
    @Transactional
    public List getActiveCustomerList(){
        return partnerDao.getActiveCustomerList();
    }
    
    @Transactional
    public List getVendorList(){
        return partnerDao.getVendorList();
    }
    
    @Transactional
    public List getActiveVendorList(){
        return partnerDao.getActiveVendorList();
    }
    
    @Transactional
    public List getNonTradeList(){
        return partnerDao.getNonTradeList();
    }
    
    @Transactional
    public List getActiveNonTradeList(){
        return partnerDao.getActiveNonTradeList();
    }
}
