/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureff.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Purnama
 */
public class GlobalFields {
    
    public static NumberFormat NUMBERFORMAT = DecimalFormat.getNumberInstance();
    public static DateFormat DATEFORMAT = new SimpleDateFormat ("dd MMM YYYY");
    
    public static int DECIMALPLACES = 2;
    
    public static String PARENT_NAMES [] = {"Customer", "Supplier", "Non Trade"}; 
    
    public static String PAYMENTTYPE_NAMES [] = {"Cash", "Transfer", "Credit Card", "Cheque / Giro", "All"};
    
    public static int CUSTOMER  = 0;
    public static int VENDOR    = 1;
    public static int NONTRADE  = 2;
    
    public static final int CASH = 0;
    public static final int TRANSFER = 1;
    public static final int CREDITCARD = 2;
    public static final int CHEQUE = 3;
    public static final int ALL = 4;
    
}
