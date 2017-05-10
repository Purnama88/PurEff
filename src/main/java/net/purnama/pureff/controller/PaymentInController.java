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
import net.purnama.pureff.entity.transactional.PaymentInEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PaymentInService;
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
public class PaymentInController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    WarehouseService warehouseService;
    
    @Autowired
    PaymentInService paymentinService;
    
    @RequestMapping(value = "api/getPaymentInList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentInList() {
        
        List<PaymentInEntity> ls = paymentinService.getPaymentInList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPaymentIn", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPaymentIn(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(paymentinService.getPaymentIn(id));
    }

    @RequestMapping(value = "api/updatePaymentIn", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePaymentIn(HttpServletRequest httpRequest,
            @RequestBody PaymentInEntity paymentin) {
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = userService.getUser(JwtUtil.parseToken(header.substring(7)));
        WarehouseEntity warehouse = warehouseService.getWarehouse(JwtUtil.parseToken2(header.substring(7)));
        
        paymentin.setLastmodified(Calendar.getInstance());
        paymentin.setWarehouse(warehouse);
        paymentin.setLastmodifiedby(user);
        
        paymentinService.updatePaymentIn(paymentin);
        
        return ResponseEntity.ok("");
    }
    
    @RequestMapping(value = "/api/getPaymentInList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPaymentInList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PaymentInEntity> ls = paymentinService.
                getPaymentInList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPaymentInList/{keyword}"},
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    public ResponseEntity<?> countPaymentInList(
            @PathVariable String keyword){
        
        return ResponseEntity.ok(paymentinService.countPaymentInList(keyword));
    }
}
