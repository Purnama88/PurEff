/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.pureff.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;



/**
 *
 * @author Purnama
 */
@WebFilter("/api/*")
public class TokenHandleFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        
        if(header == null){
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
        else{
            
            if(JwtUtil.parseToken(header.substring(7)) != null){
                
                String userid = JwtUtil.parseToken(header.substring(7));
                String warehouseid = JwtUtil.parseToken(header.substring(7));
                
                String token = JwtUtil.generateToken(userid, warehouseid);
                
                httpResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                
                chain.doFilter(request, response);
            }
            else{
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
