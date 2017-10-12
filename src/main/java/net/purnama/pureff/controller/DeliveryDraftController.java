/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.MenuEntity;
import net.purnama.pureff.entity.NumberingEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.DeliveryEntity;
import net.purnama.pureff.entity.transactional.ItemDeliveryEntity;
import net.purnama.pureff.entity.transactional.draft.DeliveryDraftEntity;
import net.purnama.pureff.entity.transactional.draft.ItemDeliveryDraftEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.DeliveryDraftService;
import net.purnama.pureff.service.DeliveryService;
import net.purnama.pureff.service.ItemDeliveryDraftService;
import net.purnama.pureff.service.ItemDeliveryService;
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
public class DeliveryDraftController {
    
    @Autowired
    DeliveryDraftService deliverydraftService;
    
    @Autowired
    DeliveryService deliveryService;
    
    @Autowired
    ItemDeliveryDraftService itemdeliverydraftService;
    
    @Autowired
    ItemDeliveryService itemdeliveryService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    NumberingService numberingService;
    
    @RequestMapping(value = "api/getDeliveryDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getDeliveryDraftList() {
        
        List<DeliveryDraftEntity> ls = deliverydraftService.getDeliveryDraftList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getDeliveryDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?>  getDeliveryDraft(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(deliverydraftService.getDeliveryDraft(id));
    }

    @RequestMapping(value = "api/addDeliveryDraft", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> addDeliveryDraft(HttpServletRequest httpRequest){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        MenuEntity menu = menuService.getMenu(3);
        
        NumberingEntity numbering = numberingService.getDefaultNumbering(menu, warehouse);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("You have not set default numbering for menu delivery");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(user);
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        
        Calendar date = Calendar.getInstance();
        
        DeliveryDraftEntity deliverydraft = new DeliveryDraftEntity();
        deliverydraft.setId(IdGenerator.generateId());
        deliverydraft.setDate(date);
        deliverydraft.setLastmodified(date);
        deliverydraft.setNumbering(numbering);
        deliverydraft.setDestination("");
        deliverydraft.setLastmodifiedby(user);
        deliverydraft.setNote("");
        deliverydraft.setVehiclenumber("");
        deliverydraft.setVehicletype("");
        deliverydraft.setWarehouse(warehouse);
        
        deliverydraftService.addDeliveryDraft(deliverydraft);
        
        return ResponseEntity.ok(deliverydraft.getId());
    }

    @RequestMapping(value = "api/updateDeliveryDraft", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateDeliveryDraft(HttpServletRequest httpRequest,
            @RequestBody DeliveryDraftEntity deliverydraft) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        deliverydraft.setLastmodified(Calendar.getInstance());
        deliverydraft.setWarehouse(warehouse);
        deliverydraft.setLastmodifiedby(user);

        deliverydraftService.updateDeliveryDraft(deliverydraft);
        
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/api/getDeliveryDraftList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getDeliveryDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<DeliveryDraftEntity> ls = deliverydraftService.
                getDeliveryDraftList(itemperpage, page, sort, keyword, user, warehouse);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countDeliveryDraftList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countDeliveryDraftList(HttpServletRequest httpRequest,
            @RequestParam(value="keyword") String keyword){
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        return ResponseEntity.ok(deliverydraftService.countDeliveryDraftList(keyword, user, warehouse));
    }
    
    @RequestMapping(value = "api/closeDeliveryDraft", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> closeDeliveryDraft(@RequestParam(value="id") String id){
        
        DeliveryDraftEntity deliverydraft = deliverydraftService.getDeliveryDraft(id);
        
        NumberingEntity numbering = deliverydraft.getNumbering();
        
        List<ItemDeliveryDraftEntity> iddelist = itemdeliverydraftService.
                getItemDeliveryDraftList(deliverydraft);
        
        if(numbering == null){
            return ResponseEntity.badRequest().body("Numbering is not valid");
        }
        else if(numbering.getNumberingname().getEnd().before(Calendar.getInstance())){
            numbering.setStatus(false);
            numbering.setLastmodified(Calendar.getInstance());
            numbering.setLastmodifiedby(deliverydraft.getLastmodifiedby());
            numberingService.updateNumbering(numbering);
            return ResponseEntity.badRequest().body("Numbering is out of date");
        }
        else if(deliverydraft.getDestination().isEmpty()){
            return ResponseEntity.badRequest().body("Destination is not valid");
        }
        else if(iddelist.isEmpty()){
            return ResponseEntity.badRequest().body("Invoice is empty");
        }
        
        if(!deliverydraft.getLastmodifiedby().isDateforward()){
            if(deliverydraft.getDate().getTime().getDate() > new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date forward");
            }
        }
        
        if(!deliverydraft.getLastmodifiedby().isDatebackward()){
            if(deliverydraft.getDate().getTime().getDate() < new Date().getDate()){
                return ResponseEntity.badRequest().body("You are not allowed to change date backward");
            }
        }
        
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setId(IdGenerator.generateId());
        delivery.setNumber(numbering.getCurrentId());
        delivery.setDate(deliverydraft.getDate());
        delivery.setPrinted(0);
        delivery.setWarehouse(deliverydraft.getWarehouse());
        delivery.setDraftid(deliverydraft.getId());
        delivery.setDestination(deliverydraft.getDestination());
        delivery.setVehicletype(deliverydraft.getVehicletype());
        delivery.setVehiclenumber(deliverydraft.getVehiclenumber());
        
        delivery.setNote(deliverydraft.getNote());
        delivery.setLastmodified(Calendar.getInstance());
        delivery.setLastmodifiedby(deliverydraft.getLastmodifiedby());
        delivery.setStatus(true);
        delivery.setUser_code(delivery.getLastmodifiedby().getCode());
        delivery.setWarehouse_code(delivery.getWarehouse().getCode());
        
        deliveryService.addDelivery(delivery);
        
        for(ItemDeliveryDraftEntity idde : iddelist){
            ItemDeliveryEntity ide = new ItemDeliveryEntity();
            ide.setId(IdGenerator.generateId());
            ide.setQuantity(idde.getQuantity());
            ide.setDescription(idde.getDescription());
            ide.setRemarks(idde.getRemark());
            ide.setDelivery(delivery);
            itemdeliveryService.addItemDelivery(ide);
        }
        
        numbering.setCurrent(numbering.getCurrent() + 1);
        
        if(numbering.getCurrent() != numbering.getEnd()){
            
        }
        else{
            numbering.setStatus(false);
        }
        
        numberingService.updateNumbering(numbering);
        
        for(ItemDeliveryDraftEntity idd : itemdeliverydraftService.getItemDeliveryDraftList(deliverydraft)){
            itemdeliverydraftService.deleteItemDeliveryDraft(idd.getId());
        }
        
        deliverydraftService.deleteDeliveryDraft(deliverydraft.getId());
        
        return ResponseEntity.ok(delivery.getId());
        
    }
    
    @RequestMapping(value = "api/deleteDeliveryDraft", method = RequestMethod.DELETE, 
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> deleteDeliveryDraft(@RequestParam(value="id") String id) {
        DeliveryDraftEntity deliverydraft = deliverydraftService.getDeliveryDraft(id);
        
        for(ItemDeliveryDraftEntity idd : itemdeliverydraftService.getItemDeliveryDraftList(deliverydraft)){
            itemdeliverydraftService.deleteItemDeliveryDraft(idd.getId());
        }
        
        deliverydraftService.deleteDeliveryDraft(deliverydraft.getId());
        
        return ResponseEntity.ok("");
    }
}
