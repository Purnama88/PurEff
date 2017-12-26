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
import java.util.Date;
import net.purnama.pureff.entity.UserEntity;

/**
 *
 * @author Purnama
 */
public class JwtUtil {
    
    private static final String secret = "pureff";
    
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
                .setIssuedAt(new Date())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public static String generateToken(String userid, String warehouseid){
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        Claims  claims = Jwts.claims().setSubject("Pur-Eff Session");
        claims.put("user", userid);
        claims.put("warehouse", warehouseid);
        
        //if it has been specified, let's add the expiration
//        if (ttlMillis >= 0) {
        long expMillis = nowMillis + 1000 * 60 *30;
        Date exp = new Date(expMillis);
//        }

        
        return Jwts.builder()
                .setIssuer("Purnama")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
