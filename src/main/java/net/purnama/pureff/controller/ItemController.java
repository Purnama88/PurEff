/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import net.purnama.pureff.entity.BuyPriceEntity;
import net.purnama.pureff.entity.ItemEntity;
import net.purnama.pureff.entity.ItemGroupEntity;
import net.purnama.pureff.entity.ItemWarehouseEntity;
import net.purnama.pureff.entity.SellPriceEntity;
import net.purnama.pureff.entity.UomEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.BuyPriceService;
import net.purnama.pureff.service.ItemGroupService;
import net.purnama.pureff.service.ItemService;
import net.purnama.pureff.service.ItemWarehouseService;
import net.purnama.pureff.service.SellPriceService;
import net.purnama.pureff.service.UomService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemController {
    
    @Autowired
    UomService uomService;
    
    @Autowired
    ItemService itemService;
    
    @Autowired
    ItemGroupService itemgroupService;
    
    @Autowired
    BuyPriceService buypriceService;
    
    @Autowired
    SellPriceService sellpriceService;
    
    @Autowired
    ItemWarehouseService itemwarehouseService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @RequestMapping(value = "api/getItemList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getItemList() {
        
        List<ItemEntity> ls = itemService.getItemList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveItemList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveItemList() {
        
        List<ItemEntity> ls = itemService.getActiveItemList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getItem", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getItem(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @RequestMapping(value="api/addItemList", method=RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addItemList(HttpServletRequest httpRequest,
            @RequestBody List<ItemEntity> itemlist) {
    
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        List<ItemEntity> returnlist = new ArrayList<>();
        
        for(ItemEntity item : itemlist){
            ItemGroupEntity itemgroup = itemgroupService.getItemGroupByCode(item.getItemgroup().getCode());
            
            if(itemgroup != null){
                
            }
            else{
                itemgroup = new ItemGroupEntity();
                itemgroup.setId(IdGenerator.generateId());
                itemgroup.setCode(item.getItemgroup().getCode());
                itemgroup.setName(item.getItemgroup().getCode());
                itemgroup.setNote("");
                itemgroup.setStatus(true);
                itemgroup.setLastmodified(Calendar.getInstance());
                itemgroup.setLastmodifiedby(user);
                itemgroupService.addItemGroup(itemgroup);
            }
            item.setItemgroup(itemgroup);
            
            UomEntity buyuom = uomService.getUomByName(item.getBuyuom().getName());
            
            if(buyuom != null){
            }
            else{
                buyuom = new UomEntity();
                buyuom.setId(IdGenerator.generateId());
                buyuom.setParent(null);
                buyuom.setValue(1);
                buyuom.setName(item.getBuyuom().getName());
                buyuom.setNote("");
                buyuom.setStatus(true);
                buyuom.setLastmodified(Calendar.getInstance());
                buyuom.setLastmodifiedby(user);
                uomService.addUom(buyuom);
            }
            item.setBuyuom(buyuom);
            
            UomEntity selluom = uomService.getUomByName(item.getSelluom().getName());
            
            if(selluom != null){
            }
            else{
                selluom = new UomEntity();
                selluom.setId(IdGenerator.generateId());
                selluom.setParent(null);
                selluom.setValue(1);
                selluom.setName(item.getSelluom().getName());
                selluom.setNote("");
                selluom.setStatus(true);
                selluom.setLastmodified(Calendar.getInstance());
                selluom.setLastmodifiedby(user);
                uomService.addUom(selluom);
            }
            item.setSelluom(selluom);
            
            item.setId(IdGenerator.generateId());
            item.setLastmodified(Calendar.getInstance());
            item.setStatus(true);
            item.setLastmodifiedby(user);
            
            try{
            
                itemService.addItem(item);

                for(WarehouseEntity temp : warehouseService.getWarehouseList()){
                    ItemWarehouseEntity itemwarehouse = new ItemWarehouseEntity();
                    itemwarehouse.setId(IdGenerator.generateId());
                    itemwarehouse.setItem(item);
                    itemwarehouse.setLastmodified(Calendar.getInstance());
                    itemwarehouse.setLastmodifiedby(user);
                    itemwarehouse.setNote("");
                    itemwarehouse.setStatus(true);
                    itemwarehouse.setStock(0);
                    itemwarehouse.setWarehouse(temp);
                    itemwarehouseService.addItemWarehouse(itemwarehouse);
                }

                buyuom = item.getBuyuom();
                BuyPriceEntity buyprice = new BuyPriceEntity();
                buyprice.setId(IdGenerator.generateId());
                buyprice.setItem(item);
                buyprice.setUom(buyuom);
                buyprice.setLastmodified(Calendar.getInstance());
                buyprice.setLastmodifiedby(user);
                buyprice.setNote("");
                buyprice.setStatus(true);
                buyprice.setValue(0);
                buypriceService.addBuyPrice(buyprice);

                for(UomEntity uom : uomService.getUomList(buyuom)){
                    BuyPriceEntity buy = new BuyPriceEntity();
                    buy.setId(IdGenerator.generateId());
                    buy.setItem(item);
                    buy.setUom(uom);
                    buy.setLastmodified(Calendar.getInstance());
                    buy.setLastmodifiedby(user);
                    buy.setNote("");
                    buy.setStatus(true);
                    buy.setValue(0);
                    buypriceService.addBuyPrice(buy);
                }

                selluom = item.getSelluom();
                SellPriceEntity sellprice = new SellPriceEntity();
                sellprice.setId(IdGenerator.generateId());
                sellprice.setItem(item);
                sellprice.setUom(selluom);
                sellprice.setLastmodified(Calendar.getInstance());
                sellprice.setLastmodifiedby(user);
                sellprice.setNote("");
                sellprice.setStatus(true);
                sellprice.setValue(0);
                sellpriceService.addSellPrice(sellprice);

                for(UomEntity uom : uomService.getUomList(selluom)){
                    SellPriceEntity sell = new SellPriceEntity();
                    sell.setId(IdGenerator.generateId());
                    sell.setItem(item);
                    sell.setUom(uom);
                    sell.setLastmodified(Calendar.getInstance());
                    sell.setLastmodifiedby(user);
                    sell.setNote("");
                    sell.setStatus(true);
                    sell.setValue(0);
                    sellpriceService.addSellPrice(sell);
                }
            }
            catch(Exception e){
                returnlist.add(item);
            }
        }
        
        return ResponseEntity.ok(returnlist);
    }
    
    @RequestMapping(value = "api/addItem", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addItem(HttpServletRequest httpRequest, @RequestBody ItemEntity item) {
        
        if(itemService.getItemByCode(item.getCode()) != null){
            return ResponseEntity.badRequest().body("Code '" + item.getCode() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(JwtUtil.parseToken2(header.substring(7)));
        
        item.setId(IdGenerator.generateId());
        item.setLastmodified(Calendar.getInstance());
        item.setLastmodifiedby(user);
        
        itemService.addItem(item);
        
        for(WarehouseEntity temp : warehouseService.getWarehouseList()){
            ItemWarehouseEntity itemwarehouse = new ItemWarehouseEntity();
            itemwarehouse.setId(IdGenerator.generateId());
            itemwarehouse.setItem(item);
            itemwarehouse.setLastmodified(Calendar.getInstance());
            itemwarehouse.setLastmodifiedby(user);
            itemwarehouse.setNote("");
            itemwarehouse.setStatus(true);
            itemwarehouse.setStock(0);
            itemwarehouse.setWarehouse(temp);
            itemwarehouseService.addItemWarehouse(itemwarehouse);
        }
        
        
        UomEntity buyuom = item.getBuyuom();
        BuyPriceEntity buyprice = new BuyPriceEntity();
        buyprice.setId(IdGenerator.generateId());
        buyprice.setItem(item);
        buyprice.setUom(buyuom);
        buyprice.setLastmodified(Calendar.getInstance());
        buyprice.setLastmodifiedby(user);
        buyprice.setNote("");
        buyprice.setStatus(true);
        buyprice.setValue(0);
        buypriceService.addBuyPrice(buyprice);
        
        for(UomEntity uom : uomService.getUomList(buyuom)){
            BuyPriceEntity buy = new BuyPriceEntity();
            buy.setId(IdGenerator.generateId());
            buy.setItem(item);
            buy.setUom(uom);
            buy.setLastmodified(Calendar.getInstance());
            buy.setLastmodifiedby(user);
            buy.setNote("");
            buy.setStatus(true);
            buy.setValue(0);
            buypriceService.addBuyPrice(buy);
        }
        
        UomEntity selluom = item.getSelluom();
        SellPriceEntity sellprice = new SellPriceEntity();
        sellprice.setId(IdGenerator.generateId());
        sellprice.setItem(item);
        sellprice.setUom(selluom);
        sellprice.setLastmodified(Calendar.getInstance());
        sellprice.setLastmodifiedby(user);
        sellprice.setNote("");
        sellprice.setStatus(true);
        sellprice.setValue(0);
        sellpriceService.addSellPrice(sellprice);
        
        for(UomEntity uom : uomService.getUomList(selluom)){
            SellPriceEntity sell = new SellPriceEntity();
            sell.setId(IdGenerator.generateId());
            sell.setItem(item);
            sell.setUom(uom);
            sell.setLastmodified(Calendar.getInstance());
            sell.setLastmodifiedby(user);
            sell.setNote("");
            sell.setStatus(true);
            sell.setValue(0);
            sellpriceService.addSellPrice(sell);
        }
        
        return ResponseEntity.ok(item);
    }

    @RequestMapping(value = "api/updateItem", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateItem(HttpServletRequest httpRequest, @RequestBody ItemEntity item) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        ItemEntity prev = itemService.getItem(item.getId());
        
        if(!item.getBuyuom().getId().equals(prev.getBuyuom().getId())){
            List<BuyPriceEntity> ls = buypriceService.getBuyPriceList(prev);
            
            for(BuyPriceEntity buyprice : ls){
                buypriceService.deleteBuyPrice(buyprice.getId());
            }
            
            UomEntity buyuom = item.getBuyuom();
            BuyPriceEntity buyprice = new BuyPriceEntity();
            buyprice.setId(IdGenerator.generateId());
            buyprice.setItem(item);
            buyprice.setUom(buyuom);
            buyprice.setLastmodified(Calendar.getInstance());
            buyprice.setLastmodifiedby(user);
            buyprice.setNote("");
            buyprice.setStatus(true);
            buyprice.setValue(0);
            buypriceService.addBuyPrice(buyprice);

            for(UomEntity uom : uomService.getUomList(buyuom)){
                BuyPriceEntity buy = new BuyPriceEntity();
                buy.setId(IdGenerator.generateId());
                buy.setItem(item);
                buy.setUom(uom);
                buy.setLastmodified(Calendar.getInstance());
                buy.setLastmodifiedby(user);
                buy.setNote("");
                buy.setStatus(true);
                buy.setValue(0);
                buypriceService.addBuyPrice(buy);
            }
        }
        
        if(!item.getSelluom().getId().equals(prev.getSelluom().getId())){
            List<SellPriceEntity> ls = sellpriceService.getSellPriceList(prev);
            
            for(SellPriceEntity sellprice : ls){
                sellpriceService.deleteSellPrice(sellprice.getId());
            }
            
            UomEntity selluom = item.getSelluom();
            SellPriceEntity sellprice = new SellPriceEntity();
            sellprice.setId(IdGenerator.generateId());
            sellprice.setItem(item);
            sellprice.setUom(selluom);
            sellprice.setLastmodified(Calendar.getInstance());
            sellprice.setLastmodifiedby(user);
            sellprice.setNote("");
            sellprice.setStatus(true);
            sellprice.setValue(0);
            sellpriceService.addSellPrice(sellprice);

            for(UomEntity uom : uomService.getUomList(selluom)){
                SellPriceEntity sell = new SellPriceEntity();
                sell.setId(IdGenerator.generateId());
                sell.setItem(item);
                sell.setUom(uom);
                sell.setLastmodified(Calendar.getInstance());
                sell.setLastmodifiedby(user);
                sell.setNote("");
                sell.setStatus(true);
                sell.setValue(0);
                sellpriceService.addSellPrice(sell);
            }
        }
        
        item.setLastmodified(Calendar.getInstance());
        item.setLastmodifiedby(user);
        
        itemService.updateItem(item);
        
        return ResponseEntity.ok(item);
    }
}
