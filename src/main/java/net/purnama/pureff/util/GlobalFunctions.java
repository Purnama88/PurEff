/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Purnama
 */
public class GlobalFunctions {
    
    public static String encrypt(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return Arrays.toString(d.digest());
    }
    
    public static double round(double value){
        DecimalFormat df;
        
        if(GlobalFields.DECIMALPLACES == 3){
            df = new DecimalFormat("#.###");
        }
        else if(GlobalFields.DECIMALPLACES == 4){
            df = new DecimalFormat("#.####");
        }
        else{
            df = new DecimalFormat("#.##");
        }
        
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        return GlobalFunctions.convertToDouble(df.format(value));
    }
    
    public static double convertToDouble(String value){
        try{
            NumberFormat format = DecimalFormat.getNumberInstance();
            Number number = format.parse(value);
            double d = number.doubleValue();
            
            return d;
        }
        catch(ParseException e){
            return 0;
        }
    }
    
    public static String getRandomString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
