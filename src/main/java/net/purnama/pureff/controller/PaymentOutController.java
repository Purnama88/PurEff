/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.entity.WarehouseEntity;
import net.purnama.pureff.entity.transactional.PaymentOutEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentOutService;
import net.purnama.pureff.service.UserService;
import net.purnama.pureff.service.WarehouseService;
import net.purnama.pureff.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class PaymentOutController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentOutService paymentoutService;
    
    @RequestMapping(value = "api/getPaymentOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentOutList() {
        
        List<PaymentOutEntity> ls = paymentoutService.getPaymentOutList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentOut", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentOut(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentoutService.getPaymentOut(id));
    }

    @RequestMapping(value = "api/updatePaymentOut", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentOut(HttpServletRequest httpRequest,
            @RequestBody PaymentOutEntity paymentout) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentout.setLastmodified(Calendar.getInstance());
        paymentout.setWarehouse(warehouse);
        paymentout.setLastmodifiedby(user);
        
        paymentoutService.updatePaymentOut(paymentout);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentOutList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentOutList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PaymentOutEntity> ls = paymentoutService.
                getPaymentOutList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentOutList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> countPaymentOutList(
            @RequestParam(value="keyword") String keyword){
        
        return ResponseEntity.ok(paymentoutService.countPaymentOutList(keyword));
    }
}
