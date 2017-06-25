/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.util;

import java.util.Date;

/**
 *
 * @author Purnama
 */
public class IdGenerator {
    public IdGenerator(){
        
    }
    
    public static String generateId(){
        return String.valueOf(new Date().getTime());
    }
    
    public static String generateInvoiceSalesId(){
        return "";
    }
}
