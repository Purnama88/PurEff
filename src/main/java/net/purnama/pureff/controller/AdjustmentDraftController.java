/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.AdjustmentEntity;
import net.purnama.pureff.entity.transactional.ItemAdjustmentEntity;
import net.purnama.pureff.entity.transactional.draft.AdjustmentDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemAdjustmentDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.AdjustmentDraftService;
import net.purnama.pureff.service.AdjustmentService;
import net.purnama.pureff.service.ItemAdjustmentDraftService;
import net.purnama.pureff.service.ItemAdjustmentService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.MenuService;
import net.purnama.pureff.service.NumberingService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */
@RestController
public class AdjustmentDraftController {
    
    @Autowired
    AdjustmentService adjustmentService;
    
    @Autowired
    ItemAdjustmentService itemadjustmentService;
    
    @Autowired
    AdjustmentDraftService adjustmentdraftService;
    
    @Autowired
    ItemAdjustmentDraftService itemadjustmentdraftService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    NumberingService numberingService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @RequestMapping(value = "api/getAdjustmentDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getAdjustmentDraftList() {
        
        List<AdjustmentDraftEntity> ls = adjustmentdraftService.getAdjustmentDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getAdjustmentDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getAdjustmentDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(adjustmentdraftService.getAdjustmentDraft(id));
    }

    @RequestMapping(value = "api/addAdjustmentDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addAdjustmentDraft(HttpServletRequest httpRequest){
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        MenuEntity menu = menuService.getMenu(1);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu adjustment");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        Calendar date = Calendar.getInstance();
        
        System.out.println(new IdGenerator().getAdjustmentId()  + "test");
        
        AdjustmentDraftEntity adjustmentdraft = new AdjustmentDraftEntity();
        adjustmentdraft.setId(IdGenerator.generateId());
        adjustmentdraft.setDate(date);
        adjustmentdraft.setLastmodified(date);
        adjustmentdraft.setLastmodifiedby(user);
        adjustmentdraft.setNote("");
        adjustmentdraft.setNumbering(numbering);
        adjustmentdraft.setStatus(true);
        adjustmentdraft.setWarehouse(warehouse);
        
        adjustmentdraftService.addAdjustmentDraft(adjustmentdraft);
        
        return ResponseEntity.ok(adjustmentdraft.getId());
    }

    @RequestMapping(value = "api/closeAdjustmentDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeAdjustmentDraft(@RequestParam(value="id") String id) {
        AdjustmentDraftEntity adjustmentdraft = adjustmentdraftService.getAdjustmentDraft(id);
        
        NumberingEntity numbering = adjustmentdraft.getNumbering();
        
        List<ItemAdjustmentDraftEntity> iisdelist = itemadjustmentdraftService.
                getItemAdjustmentDraftList(adjustmentdraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(adjustmentdraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(iisdelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        AdjustmentEntity adjustment = new AdjustmentEntity();
        adjustment.setId(IdGenerator.generateId());
        adjustment.setNumber(numbering.getCurrentId());
        adjustment.setDate(adjustmentdraft.getDate());
        adjustment.setPrinted(0);
        adjustment.setWarehouse(adjustmentdraft.getWarehouse());
        adjustment.setDraftid(adjustmentdraft.getId());
        adjustment.setNote(adjustmentdraft.getNote());
        adjustment.setLastmodified(Calendar.getInstance());
        adjustment.setLastmodifiedby(adjustmentdraft.getLastmodifiedby());
        adjustment.setStatus(true);
        adjustment.setUser_code(adjustment.getLastmodifiedby().getCode());
        adjustment.setWarehouse_code(adjustment.getWarehouse().getCode());
        adjustmentService.addAdjustment(adjustment);
        
        for(ItemAdjustmentDraftEntity iisde : iisdelist){
            ItemAdjustmentEntity iise = new ItemAdjustmentEntity();
            iise.setId(IdGenerator.generateId());
            iise.setQuantity(iisde.getQuantity());
            iise.setItem(iisde.getItem());
            
            ItemWarehouseEntity iw = itemwarehouseService.
                    getItemWarehouse(adjustment.getWarehouse(), iise.getItem());
            
            iise.setTstock(iw.getStock());
            iise.setRemark(iisde.getRemark());
            iise.setAdjustment(adjustment);
            iise.setItem_name(iisde.getItem().getName());
            iise.setItem_code(iisde.getItem().getCode());
            itemadjustmentService.addItemAdjustment(iise);
            
            iw.setStock(iisde.getQuantity());
            itemwarehouseService.updateItemWarehouse(iw);
        }
        
        numbering.setCurrent(numbering.getCurrent()+1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemAdjustmentDraftEntity idd : itemadjustmentdraftService.getItemAdjustmentDraftList(adjustmentdraft)){
            itemadjustmentdraftService.deleteItemAdjustmentDraft(idd.getId());
        }
        
        adjustmentdraftService.deleteAdjustmentDraft(id);
        
        return ResponseEntity.ok(adjustment.getId());
    }
    
    @RequestMapping(value = "api/updateAdjustmentDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public void updateAdjustmentDraft(HttpServletRequest httpRequest,
            @RequestBody AdjustmentDraftEntity adjustmentdraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        adjustmentdraft.setLastmodified(Calendar.getInstance());
        adjustmentdraft.setWarehouse(warehouse);
        adjustmentdraft.setLastmodifiedby(user);
        
        adjustmentdraftService.updateAdjustmentDraft(adjustmentdraft);
    }

    @RequestMapping(value = "api/deleteAdjustmentDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public void deleteAdjustmentDraft(@RequestParam(value="id") String id) {
        AdjustmentDraftEntity adjustmentdraft = adjustmentdraftService.getAdjustmentDraft(id);
        
        for(ItemAdjustmentDraftEntity idd : itemadjustmentdraftService.getItemAdjustmentDraftList(adjustmentdraft)){
            itemadjustmentdraftService.deleteItemAdjustmentDraft(idd.getId());
        }
        
        adjustmentdraftService.deleteAdjustmentDraft(id);		
    }
    
    @RequestMapping(value = "/api/getAdjustmentDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getAdjustmentDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        List<AdjustmentDraftEntity> ls = adjustmentdraftService.
                getAdjustmentDraftList(itemperpage, page, sort, keyword, user, warehouse);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countAdjustmentDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countAdjustmentDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId((JwtUtil.parseToken2(header.substring(7))));
        
        return ResponseEntity.ok(adjustmentdraftService.countAdjustmentDraftList(keyword, user, warehouse));
    }
}
