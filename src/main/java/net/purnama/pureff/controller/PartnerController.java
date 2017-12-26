/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.purnama.pureff.entity.PartnerEntity;
import net.purnama.pureff.entity.PartnerTypeEntity;
import net.purnama.pureff.entity.UserEntity;
import net.purnama.pureff.security.JwtUtil;
import net.purnama.pureff.service.PartnerService;
import net.purnama.pureff.service.PartnerTypeService;
import net.purnama.pureff.tablemodel.PartnerTableModel;
import net.purnama.pureff.util.GlobalFields;
import net.purnama.pureff.util.IdGenerator;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class PartnerController {
    @Autowired
    PartnerService partnerService;
    
    @Autowired
    PartnerTypeService partnertypeService;
    
    @RequestMapping(value = "api/getActivePartnerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActivePartnerList() {
        
        List<PartnerEntity> ls = partnerService.getActivePartnerList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPartnerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getPartnerList() {
        
        List<PartnerEntity> ls = partnerService.getPartnerList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getCustomerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getCustomerList() {
        
        List<PartnerEntity> ls = partnerService.getCustomerList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveCustomerList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveCustomerList() {
        
        List<PartnerEntity> ls = partnerService.getActiveCustomerList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getVendorList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getVendorList() {
        
        List<PartnerEntity> ls = partnerService.getVendorList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveVendorList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveVendorList() {
        
        List<PartnerEntity> ls = partnerService.getActiveVendorList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getNonTradeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getNonTradeList() {
        
        List<PartnerEntity> ls = partnerService.getNonTradeList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getActiveNonTradeList", method = RequestMethod.GET, 
            headers = "Accept=application/json")
    public ResponseEntity<?> getActiveNonTradeList() {
        
        List<PartnerEntity> ls = partnerService.getActiveNonTradeList();
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = "api/getPartner", method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"id"})
    public ResponseEntity<?> getPartner(@RequestParam(value="id") String id) {
        return ResponseEntity.ok(partnerService.getPartner(id));
    }

    @RequestMapping(value = "api/addPartner", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> addPartner(HttpServletRequest httpRequest, @RequestBody PartnerEntity partner) {
        
        if(partnerService.getPartnerByCode(partner.getCode()) != null){
            return ResponseEntity.badRequest().body("Code '" + partner.getCode() +"' already exist");
        }
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partner.setId(IdGenerator.generateId());
        partner.setLastmodified(Calendar.getInstance());
        partner.setBalance(0);
        partner.setLastmodifiedby(user);
        
        partnerService.addPartner(partner);
        
        return ResponseEntity.ok(partner);
    }

    @RequestMapping(value = "api/updatePartner", method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updatePartner(HttpServletRequest httpRequest,
            @RequestBody PartnerEntity partner) {
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        UserEntity user = new UserEntity();
        user.setId(JwtUtil.parseToken(header.substring(7)));
        
        partner.setLastmodified(Calendar.getInstance());
        partner.setLastmodifiedby(user);
        
        partnerService.updatePartner(partner);
        
        return ResponseEntity.ok(partner);
    }
    
    @RequestMapping(value = "/api/getPartnerList", method = RequestMethod.GET, 
            headers = "Accept=application/json", params = {"itemperpage", "page", "sort", "keyword"})
    public ResponseEntity<?> getPartnerList(
            @RequestParam(value="itemperpage") int itemperpage,
            @RequestParam(value="page") int page,
            @RequestParam(value="sort") String sort,
            @RequestParam(value="keyword") String keyword) {
        
        List<PartnerEntity> ls = partnerService.getPartnerList(itemperpage, page, sort, keyword);
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/countPartnerList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {"keyword"})
    public ResponseEntity<?> countPartnerList(@RequestParam(value="keyword") String keyword){
        return ResponseEntity.ok(partnerService.countPartnerList(keyword));
    }
    
    @RequestMapping(value = {"api/getPartnerList"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {
                "partnertypeid", "status"})
    public ResponseEntity<?> getPartnerList(
            HttpServletRequest httpRequest,
            @RequestParam(value="partnertypeid") String partnertypeid,
            @RequestParam(value="status") boolean status){
        
        PartnerTypeEntity partnertype = new PartnerTypeEntity();
        partnertype.setId(partnertypeid);
        
        
        List<PartnerEntity> ls = partnerService.
                getPartnerList(partnertype, status);
        
        return ResponseEntity.ok(ls);
    }
    
    @RequestMapping(value = {"api/getPartnerReport"},
            method = RequestMethod.GET,
            headers = "Accept=application/json", params = {
                "partnertypeid", "status"})
    public ResponseEntity<?> getPartnerReport(
            HttpServletRequest httpRequest,
            @RequestParam(value="partnertypeid") String partnertypeid,
            @RequestParam(value="status") boolean status) throws IOException, JRException{
        
        HttpHeaders responseHeaders = new HttpHeaders();
        
        PartnerTypeEntity partnertype = partnertypeService.getPartnerType(partnertypeid);
        
        List<PartnerEntity> list = partnerService.
                getPartnerList(partnertype, status);
        
        HashMap map = new HashMap();
        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
        map.put("PARTNERTYPE", partnertype.getName());
        if(status){
            map.put("STATUS", "ACTIVE");
        }
        else{
            map.put("STATUS", "INACTIVE");
        }

        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("net/purnama/template/PartnerBalanceReport.jasper");

        InputStream is = imageURL.openStream();
        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
            map, new JRTableModelDataSource(new PartnerTableModel(list)));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        responseHeaders.add("content-disposition", "attachment; filename=Partner.pdf");
        responseHeaders.add("Content-Type","application/octet-stream");

        ResponseEntity re = new ResponseEntity(baos.toByteArray(), responseHeaders,HttpStatus.OK);
        
        return re;
    }
}
