/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.purnama.pureff.entity.UserEntity;

/**
 *
 * @author Purnama
 */
public class JwtUtil {
    
    private static final String secret = "wiliana";
    
    public static String parseToken2(String token){
        try{
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            
            String userid = (String)body.get("warehouse");
            
            return userid;
        }
        catch(JwtException | ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static String parseToken(String token){
        try{
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            
            String userid = (String)body.get("user");
            
            return userid;
        }
        catch(JwtException | ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }
    
//    public static UserEntity parseToken2(String token){
//        try{
//            Claims body = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//            
//            UserEntity user = body.get("user", UserEntity.class);
//            
////            LoginEntity le = new LoginEntity();
////            le.setUsername((String)body.get("username"));
////            le.setPassword((String)body.get("password"));
////            le.setWarehouseid((String)body.get("warehouseid"));
//            
//            return user;
//        }
//        catch(JwtException | ClassCastException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    public static String generateToken(UserEntity user){
        
        Claims  claims = Jwts.claims().setSubject("Pur-Eff Session");
        claims.put("user", user);
//        claims.put("warehuse", warehouseid);
        
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public static String generateToken(String userid, String warehouseid){
        
        Claims  claims = Jwts.claims().setSubject("Pur-Eff Session");
        claims.put("user", userid);
        claims.put("warehouse", warehouseid);
        
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
