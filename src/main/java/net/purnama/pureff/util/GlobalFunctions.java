/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.util;

import java.util.Arrays;

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
}
