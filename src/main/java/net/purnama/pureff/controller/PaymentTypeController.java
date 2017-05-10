/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.controller;

import java.util.ArrayList;
import java.util.List;
import net.purnama.pureff.util.GlobalFields;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Purnama
 */

@RestController
public class PaymentTypeController {
    
    @RequestMapping(value = "api/getPaymentTypeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPaymentTypeList() {
        
        List<String> ls = new ArrayList<>();
        
        for(String name : GlobalFields.PAYMENTTYPE_NAMES){
            ls.add(name);
        }
        
        return ResponseEntity.ok(ls);
    }
    
}
