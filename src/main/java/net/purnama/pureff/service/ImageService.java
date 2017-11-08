/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.ImageDao;
import net.purnama.pureff.entity.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author purnama
 */
@Service("imageService")
public class ImageService {
    @Autowired
    ImageDao imageDao;
    
    @Transactional
    public ImageEntity getImage(String id) {
            return imageDao.getImage(id);
    }
    
    @Transactional
    public List getImageList(int itemperpage, int page, String sort, String keyword){
        return imageDao.getImageList(itemperpage, page, sort, keyword);
    }

    @Transactional
    public int countImageList(String keyword){
        return imageDao.countImageList(keyword);
    }
}
