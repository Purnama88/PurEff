/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.NumberingNameEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.NumberingNameService;
import net.purnama.pureff.util.CalendarUtil;
import net.purnama.pureff.util.DateUtils;
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
public class NumberingNameController {
    
    @Autowired
    NumberingNameService numberingnameService;
    
    @RequestMapping(value = "api/getNumberingNameList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getNumberingNameList() {
        
        List<NumberingNameEntity> ls = numberingnameService.getNumberingNameList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveNumberingNameList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?>  getActiveNumberingNameList() {
        
        List<NumberingNameEntity> ls = numberingnameService.getActiveNumberingNameList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getNumberingName", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getNumberingName(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(numberingnameService.getNumberingName(id));
    }

    @RequestMapping(value = "api/addNumberingName", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addNumberingName(HttpServletRequest httpRequest,
            @RequestBody NumberingNameEntity numberingname) {
        
        if(numberingnameService.getNumberingNameByName(numberingname.getName()) != null){
            return ResponseEntity.badRequest().body("Name '" + numberingname.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numberingname.setBegin(CalendarUtil.toStartOfDay(numberingname.getBegin()));
        numberingname.setEnd(CalendarUtil.toEndofDay(numberingname.getEnd()));
        numberingname.setId(IdGenerator.generateId());
        numberingname.setLastmodified(Calendar.getInstance());
        numberingname.setLastmodifiedby(temp);
        
        if(checkDateCollision("", numberingname.getBegin(), numberingname.getEnd())){
            return ResponseEntity.badRequest().body("You have a date collision with other numbering name");
        }
        
        numberingnameService.addNumberingName(numberingname);
        
        return ResponseEntity.ok(numberingname);
    }

    @RequestMapping(value = "api/updateNumberingName", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateNumberingName(HttpServletRequest httpRequest,
            @RequestBody NumberingNameEntity numberingname) {
        
        NumberingNameEntity prev = numberingnameService.getNumberingNameByName(numberingname.getName());
        
        if(prev != null && !prev.getId().equals(numberingname.getId())){
            return ResponseEntity.badRequest().body("Name '" + numberingname.getName() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity temp = new UserEntity();
        temp.setId(JwtUtil.parseToken(header.substring(7)));
        
        numberingname.setBegin(CalendarUtil.toStartOfDay(numberingname.getBegin()));
        numberingname.setEnd(CalendarUtil.toEndofDay(numberingname.getEnd()));
        numberingname.setLastmodified(Calendar.getInstance());
        numberingname.setLastmodifiedby(temp);
        
        if(checkDateCollision(numberingname.getId(), numberingname.getBegin(), numberingname.getEnd())){
            return ResponseEntity.badRequest().body("You have a date collision with other numbering name");
        }
        
        numberingnameService.updateNumberingName(numberingname);
        
        return ResponseEntity.ok(numberingname);
    }
    
    @RequestMapping(value = "/api/getNumberingNameList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getNumberingNameList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<NumberingNameEntity> ls = numberingnameService.getNumberingNameList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countNumberingNameList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countNumberingNameList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(numberingnameService.countNumberingNameList(keyword));
    }
    
    protected boolean checkDateCollision(String exid, Calendar begin, Calendar end){
        boolean status = false;
        
        for(NumberingNameEntity numberingname : numberingnameService.getActiveNumberingNameList()){
            
            if(exid.equals(numberingname.getId())){
                
            }
            else{
                if(DateUtils.isBetween(begin, numberingname.getBegin(), numberingname.getEnd()) ||
                        DateUtils.isBetween(end, numberingname.getBegin(), numberingname.getEnd()) ||
                        DateUtils.isBetween(numberingname.getBegin(), begin, end) ||
                        DateUtils.isBetween(numberingname.getEnd(), begin, end)){

                        status = true;
                        break;
                }
            }
        }
        return status;
    }
}
