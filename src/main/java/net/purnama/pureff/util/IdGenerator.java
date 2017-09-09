/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Purnama
 */
public class IdGenerator {
    Properties idprof = new Properties();
    
    public IdGenerator(){
        initId();
    }
    
    public int getAdjustmentId(){
        return Integer.parseInt(idprof.getProperty("ADJUSTMENTDRAFT"));
    }
    
    public static String generateId(){
        return String.valueOf(new Date().getTime());
    }
    
    public static String generateInvoiceSalesId(){
        return "";
    }
    
    public final void initId(){
	InputStream input = null;

	try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            input = classLoader.getResourceAsStream("/resources/net/purnama/transaction/Id.properties");
            
            
            
//            System.out.println(input.);
                   
            // load a properties file
            idprof.load(input);
            
          
	}
        catch (IOException ex){
            ex.printStackTrace();
            
	}
        finally{
            if (input != null){
                try{
                    input.close();
                }
                catch (IOException e) {
                }
            }
	}
    }
}
