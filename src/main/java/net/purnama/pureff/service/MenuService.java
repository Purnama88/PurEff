/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.MenuDao;
import net.purnama.pureff.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("menuService")
public class MenuService {
    
    @Autowired
    MenuDao menuDao;

    @Transactional
    public List<MenuEntity> getMenuList() {
            return menuDao.getMenuList();
    }
    
    @Transactional
    public List<MenuEntity> getTransactionalMenuList() {
            return menuDao.getTransactionalMenuList();
    }

    @Transactional
    public MenuEntity getMenu(int id) {
            return menuDao.getMenu(id);
    }

    @Transactional
    public void addMenu(MenuEntity menu) {
            menuDao.addMenu(menu);
    }
}