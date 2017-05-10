/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.service;

import java.util.List;
import javax.transaction.Transactional;
import net.purnama.pureff.dao.RoleDao;
import net.purnama.pureff.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Purnama
 */
@Service("roleService")
public class RoleService {
    
    @Autowired
    RoleDao roleDao;

    @Transactional
    public List<RoleEntity> getRoleList() {
            return roleDao.getRoleList();
    }
    
    @Transactional
    public List<RoleEntity> getActiveRoleList() {
            return roleDao.getActiveRoleList();
    }
    
    @Transactional
    public RoleEntity getRole(String id) {
            return roleDao.getRole(id);
    }
    
    @Transactional
    public RoleEntity getRoleByName(String name) {
            return roleDao.getRoleByName(name);
    }

    @Transactional
    public void addRole(RoleEntity role) {
            roleDao.addRole(role);
    }

    @Transactional
    public void updateRole(RoleEntity role) {
            roleDao.updateRole(role);
    }
    
    @Transactional
    public List getRoleList(
            int itemperpage, int page, String sort, String keyword){
        return roleDao.getRoleList(itemperpage, page, sort, keyword);
    }
    
    @Transactional
    public int countRoleList(String keyword){
        return roleDao.countRoleList(keyword);
    }
    
}
