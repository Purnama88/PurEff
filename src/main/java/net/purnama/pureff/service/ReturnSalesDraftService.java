/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ReturnSalesDraftDao;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.draft.ReturnSalesDraftEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("returnsalesdraftService")
public class ReturnSalesDraftService {
    
    @Autowired
    ReturnSalesDraftDao returnsalesdraftDao;

    @Transactional
    public List<ReturnSalesDraftEntity> getReturnSalesDraftList() {
            return returnsalesdraftDao.getReturnSalesDraftList();
    }

    @Transactional
    public ReturnSalesDraftEntity getReturnSalesDraft(String id) {
            return returnsalesdraftDao.getReturnSalesDraft(id);
    }

    @Transactional
    public void addReturnSalesDraft(ReturnSalesDraftEntity returnsalesdraft) {
            returnsalesdraftDao.addReturnSalesDraft(returnsalesdraft);
    }

    @Transactional
    public void updateReturnSalesDraft(ReturnSalesDraftEntity returnsalesdraft) {
            returnsalesdraftDao.updateReturnSalesDraft(returnsalesdraft);
    }

    @Transactional
    public void deleteReturnSalesDraft(String id) {
            returnsalesdraftDao.deleteReturnSalesDraft(id);
    }
    
    @Transactional
    public List getReturnSalesDraftList(int itemperpage, int page, String sort,
            String keyword, UserEntity user, WarehouseEntity warehouse){
        return returnsalesdraftDao.getReturnSalesDraftList(itemperpage, page, sort, keyword, user, warehouse);
    }
    
    @Transactional
    public int countReturnSalesDraftList(String keyword, UserEntity user, WarehouseEntity warehouse){
        return returnsalesdraftDao.countReturnSalesDraftList(keyword, user, warehouse);
    }
}