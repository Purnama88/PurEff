/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.UserDao;
import net.purnama.pureff.dao.WarehouseDao;
import net.purnama.pureff.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("userService")
public class UserService {
    
    @Autowired
    UserDao userDao;
    
    @Transactional
    public UserEntity getUser(String username, String password) {
            return userDao.getUser(username, password);
    }
    
    @Transactional
    public List<UserEntity> getUserList() {
            return userDao.getUserList();
    }
    
    @Transactional
    public List<UserEntity> getUserList(int itemperpage, int page, String keyword) {
            return userDao.getUserList(itemperpage, page, keyword);
    }
    
    @Transactional
    public UserEntity getUser(String id) {
            return userDao.getUser(id);
    }
    
    @Transactional
    public void addUser(UserEntity user) {
            userDao.addUser(user);
    }

    @Transactional
    public void updateUser(UserEntity user) {
            userDao.updateUser(user);
    }
    
    @Transactional
    public int countUserList(String keyword) {
            return userDao.countUserList(keyword);
    }
}